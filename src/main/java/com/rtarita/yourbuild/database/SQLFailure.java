package com.rtarita.yourbuild.database;

import java.sql.SQLException;

public class SQLFailure extends RuntimeException {
    public SQLFailure(String message, SQLException cause) {
        super(message, cause);
    }
}
