package com.rtarita.yourbuild.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class SQLExceptionUtil {
    private SQLExceptionUtil() {
    }

    public static List<SQLException> extractFrom(SQLException original) {
        List<SQLException> res = new ArrayList<>();
        SQLException current = original;
        while (current != null) {
            res.add(current);
            current = current.getNextException();
        }
        return res;
    }

    public static void logException(String action, SQLException original) {
        List<SQLException> extracted = SQLExceptionUtil.extractFrom(original);
        System.out.println("Failed to " + action + ". Failed with " + extracted.size() + " exception(s):");
        System.out.println(
            extracted.stream()
                .map(Throwable::getMessage)
                .collect(Collectors.joining("\n"))
        );
    }

    public static <R> R getOrThrow(DatabaseSupplier<R> function, String action, String errmsg) {
        try {
            return function.get();
        } catch (SQLException exc) {
            SQLExceptionUtil.logException(action, exc);
            throw new SQLFailure(errmsg, exc);
        }
    }

    public static void runOrThrow(DatabaseRunnable function, String action, String errmsg) {
        try {
            function.run();
        } catch (SQLException exc) {
            SQLExceptionUtil.logException(action, exc);
            throw new SQLFailure(errmsg, exc);
        }
    }
}
