package com.rtarita.yourbuild.database;

import com.rtarita.yourbuild.util.Pair;
import org.intellij.lang.annotations.Language;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DatabaseHandle implements Closeable {
    private static final Set<Class<?>> NUMERIC_TYPES = Set.of(
        BigDecimal.class,
        Boolean.class,
        Byte.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class
    );
    private final String dbUrl;
    private final Connection connection;
    private final Map<String, PreparedStatement> ddlStatements = new HashMap<>();
    private final Map<String, PreparedStatement> dmlStatements = new HashMap<>();
    private final Map<String, PreparedStatement> sqlStatements = new HashMap<>();

    private void prepareStatement(Map<String, PreparedStatement> stmtMap, String key, String statement) {
        try {
            stmtMap.put(key, connection.prepareStatement(statement));
        } catch (SQLException exc) {
            SQLExceptionUtil.logException("prepare the statement '" + statement + "'", exc);
            throw new RuntimeException("Statement preparation failed.");
        }
    }

    private PreparedStatement insertParams(Map<String, PreparedStatement> stmtMap, String key, List<Object> parameters) {
        PreparedStatement statement = stmtMap.get(key);
        if (statement == null) throw new IllegalArgumentException("Unknown statement key: " + key);

        ParameterMetaData metadata = SQLExceptionUtil.getOrThrow(
            statement::getParameterMetaData,
            "retrieve parameter metadata",
            "Parameter metadata retrieval failed."
        );
        int paramCount = SQLExceptionUtil.getOrThrow(
            metadata::getParameterCount,
            "retrieve parameter count",
            "Parameter count retrieval failed."
        );

        if (paramCount != parameters.size()) {
            throw new IllegalArgumentException("Wrong number of parameters. Expected " + paramCount + " but got " + parameters.size());
        }

        int counter = 1;
        for (Object param : parameters) {
            final int finalCounter = counter;
            if (param == null) {
                int nullability = SQLExceptionUtil.getOrThrow(
                    () -> metadata.isNullable(finalCounter),
                    "retrieve nullability of parameter",
                    "Nullability check failed."
                );
                if (nullability == ParameterMetaData.parameterNoNulls) {
                    throw new IllegalArgumentException("Parameter #" + counter + " is not nullable but 'null' was given");
                } else {
                    int sqlType = SQLExceptionUtil.getOrThrow(
                        () -> metadata.getParameterType(finalCounter),
                        "retrieve parameter type",
                        "Parameter type retrieval failed."
                    );
                    SQLExceptionUtil.runOrThrow(
                        () -> statement.setNull(finalCounter, sqlType),
                        "set parameter",
                        "Setting parameter failed."
                    );

                }
            } else {
                String paramClassName = SQLExceptionUtil.getOrThrow(
                    () -> metadata.getParameterClassName(finalCounter),
                    "retrieve parameter class",
                    "Parameter class retrieval failed."
                );

                Class<?> paramClass;
                try {
                    paramClass = Class.forName(paramClassName);
                } catch (ClassNotFoundException exc) {
                    throw new IllegalArgumentException("unknown parameter class '" + paramClassName + "'");
                }

                if (!paramClass.isAssignableFrom(param.getClass())) {
                    int sqlType = SQLExceptionUtil.getOrThrow(
                        () -> metadata.getParameterType(finalCounter),
                        "retrieve parameter type",
                        "Parameter type retrieval failed."
                    );
                    if (
                        (!paramClass.equals(String.class) || !param.getClass().equals(Character.class))
                            && (sqlType != Types.NUMERIC || !NUMERIC_TYPES.contains(param.getClass()))
                    ) {
                        throw new IllegalArgumentException(
                            "Wrong parameter type. Expected "
                                + paramClass.getSimpleName()
                                + " but got "
                                + param.getClass().getSimpleName()
                        );
                    }
                }

                SQLExceptionUtil.runOrThrow(
                    () -> statement.setObject(finalCounter, param),
                    "set parameter",
                    "Setting parameter failed."
                );
            }
            ++counter;
        }
        return statement;
    }

    private void closeStatements(Map<String, PreparedStatement> stmtMap) {
        for (PreparedStatement stmt : ddlStatements.values()) {
            SQLExceptionUtil.runOrThrow(
                stmt::close,
                "close prepared statement",
                "Statement closing failed."
            );
        }
    }

    private Map<Integer, String> getColumnNames(ResultSetMetaData metadata, int columns) {
        return IntStream.range(1, columns + 1)
            .mapToObj(idx -> new Pair<>(
                idx,
                SQLExceptionUtil.getOrThrow(
                    () -> metadata.getColumnName(idx),
                    "retrieve a column name",
                    "Column name retrieval failed."
                )
            ))
            .collect(Collectors.toMap(Pair::first, Pair::second));
    }

    public DatabaseHandle(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        connection = SQLExceptionUtil.getOrThrow(
            () -> DriverManager.getConnection(dbUrl, user, password),
            "connect to DB '" + dbUrl + "'",
            "Connection failed."
        );
        SQLExceptionUtil.runOrThrow(
            () -> connection.setAutoCommit(true),
            "set auto-commit to true",
            "Failed to turn on auto-commit."
        );
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void prepareDDLStatement(String key, @Language("SQL") String statement) {
        prepareStatement(ddlStatements, key, statement);
    }

    public void prepareDMLStatement(String key, @Language("SQL") String statement) {
        prepareStatement(dmlStatements, key, statement);
    }

    public void prepareSQLStatement(String key, @Language("SQL") String statement) {
        prepareStatement(sqlStatements, key, statement);
    }

    public void executeDDL(String key, List<Object> parameters) {
        PreparedStatement statement = insertParams(ddlStatements, key, parameters);
        SQLExceptionUtil.runOrThrow(
            statement::executeUpdate,
            "execute DDL query",
            "DDL statement execution failed."
        );
    }

    public int executeDML(String key, List<Object> parameters) {
        PreparedStatement statement = insertParams(dmlStatements, key, parameters);
        return SQLExceptionUtil.getOrThrow(
            statement::executeUpdate,
            "execute DML query",
            "DML statement execution failed."
        );
    }

    public ResultSet executeSQL(String key, List<Object> parameters) {
        PreparedStatement statement = insertParams(sqlStatements, key, parameters);
        return SQLExceptionUtil.getOrThrow(
            statement::executeQuery,
            "execute SQL query",
            "SQL statement execution failed."
        );
    }

    public List<Map<String, Object>> executeSQLAndUnwind(String key, List<Object> parameters) {
        return unwindResultSet(executeSQL(key, parameters));
    }

    public void executeDDL(String key) {
        executeDDL(key, Collections.emptyList());
    }

    public int executeDML(String key) {
        return executeDML(key, Collections.emptyList());
    }

    public ResultSet executeSQL(String key) {
        return executeSQL(key, Collections.emptyList());
    }

    public List<Map<String, Object>> executeSQLAndUnwind(String key) {
        return unwindResultSet(executeSQL(key));
    }

    public void transactional(Consumer<DatabaseHandle> transaction) {
        SQLExceptionUtil.runOrThrow(
            () -> connection.setAutoCommit(false),
            "set auto-commit to false",
            "Failed to turn off auto-commit."
        );
        boolean successful = false;
        try {
            transaction.accept(this);
            successful = true;
        } catch (SQLFailure f) {
            System.out.println("Transaction failed, rolling back...");
            SQLExceptionUtil.runOrThrow(
                connection::rollback,
                "roll back the transaction",
                "Rolling back transaction failed."
            );
        }
        if (successful) {
            SQLExceptionUtil.runOrThrow(
                connection::commit,
                "commit the transaction",
                "Committing transaction failed."
            );
        }
        SQLExceptionUtil.runOrThrow(
            () -> connection.setAutoCommit(true),
            "set auto-commit to true",
            "Failed to turn on auto-commit."
        );
    }

    public List<Map<String, Object>> unwindResultSet(ResultSet rs) {
        int size = SQLExceptionUtil.getOrThrow(
            rs::getFetchSize,
            "get the fetch size of a result set",
            "Fetch size retrieval failed."
        );
        ResultSetMetaData metadata = SQLExceptionUtil.getOrThrow(
            rs::getMetaData,
            "get the metadata of a result set",
            "Metadata retrieval failed."
        );
        int columns = SQLExceptionUtil.getOrThrow(
            metadata::getColumnCount,
            "get the column count of a result set",
            "Column count retrieval failed."
        );
        Map<Integer, String> columnNames = getColumnNames(metadata, columns);
        List<Map<String, Object>> result = new ArrayList<>(size);
        while (SQLExceptionUtil.getOrThrow(rs::next, "get the next row on a result set", "ResultSet advancement failed.")) {
            result.add(IntStream.range(1, columns + 1)
                .mapToObj(idx -> new Pair<>(
                    columnNames.get(idx),
                    SQLExceptionUtil.getOrThrow(
                        () -> rs.getObject(idx),
                        "retrieve value from result set",
                        "Value retrieval failed."
                    )
                ))
                .collect(Collectors.toMap(Pair::first, Pair::second)));
        }
        SQLExceptionUtil.runOrThrow(
            rs::close,
            "close result set",
            "Result set closing failed."
        );
        return result;
    }

    @Override
    public void close() {
        closeStatements(ddlStatements);
        closeStatements(dmlStatements);
        closeStatements(sqlStatements);
        SQLExceptionUtil.runOrThrow(
            connection::close,
            "close the database connection",
            "Connection closing failed."
        );
    }
}
