package com.rtarita.yourbuild.database;

import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseSupplier<R> {
    R get() throws SQLException;
}
