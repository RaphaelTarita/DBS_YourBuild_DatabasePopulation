package com.rtarita.yourbuild.model;

import java.util.List;
import java.util.Map;

public interface Model {
    Map<String, Object> primaryKey();

    Map<List<String>, List<Object>> foreignKeys();

    Map<String, Object> insertStatementParameters();
}
