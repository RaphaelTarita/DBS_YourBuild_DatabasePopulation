package com.rtarita.yourbuild.database;

import com.rtarita.yourbuild.model.ModelSchema;

import java.util.List;

public record Reference(Class<? extends ModelSchema<?>> target, List<String> targetColumns) {
}
