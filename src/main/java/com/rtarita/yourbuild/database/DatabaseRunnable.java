package com.rtarita.yourbuild.database;

import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseRunnable {
    void run() throws SQLException;
}
