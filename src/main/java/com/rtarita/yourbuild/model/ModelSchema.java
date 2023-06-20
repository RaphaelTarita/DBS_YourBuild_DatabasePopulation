package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ModelSchema<M extends Model> {
    String tableName();

    List<String> allColumnNames();

    List<String> insertableColumnNames();

    List<String> nonDefaultColumnNames();

    Set<String> columnsOmittedByGeneration();

    M generate(Faker faker);

    Set<String> primaryKeyColumns();

    Map<List<String>, Reference> foreignKeyColumns();
}
