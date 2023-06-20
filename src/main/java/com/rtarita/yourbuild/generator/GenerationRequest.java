package com.rtarita.yourbuild.generator;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.model.Model;
import com.rtarita.yourbuild.model.ModelSchema;

import java.util.Objects;

public class GenerationRequest<M extends Model, MS extends ModelSchema<M>> {
    public static class Builder<M extends Model, MS extends ModelSchema<M>> {
        private final MS modelSchema;
        private Faker faker = null;
        private int generationCount = 10;

        private Builder(MS modelSchema) {
            this.modelSchema = modelSchema;
        }

        public Builder<M, MS> faker(Faker faker) {
            this.faker = faker;
            return this;
        }

        public Builder<M, MS> generationCount(int generationCount) {
            this.generationCount = generationCount;
            return this;
        }

        public GenerationRequest<M, MS> build() {
            if (modelSchema == null) throw new IllegalArgumentException("ModelSchema was not specified");
            return new GenerationRequest<>(
                modelSchema,
                faker == null ? new Faker() : faker,
                generationCount
            );
        }
    }

    private final MS modelSchema;
    private final Faker faker;
    private final int generationCount;

    private GenerationRequest(MS modelSchema, Faker faker, int generationCount) {
        this.modelSchema = modelSchema;
        this.faker = faker;
        this.generationCount = generationCount;
    }

    public static <M extends Model, MS extends ModelSchema<M>> GenerationRequest.Builder<M, MS> builder(MS modelSchema) {
        return new Builder<>(modelSchema);
    }

    public MS getModelSchema() {
        return modelSchema;
    }

    public Faker getFaker() {
        return faker;
    }

    public int getGenerationCount() {
        return generationCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenerationRequest<?, ?> that)) return false;
        return generationCount == that.generationCount && modelSchema.equals(that.modelSchema) && faker.equals(that.faker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelSchema, faker, generationCount);
    }

    @Override
    public String toString() {
        return "GenerationRequest{" +
            "modelSchema=" + modelSchema +
            ", faker=" + faker +
            ", generationCount=" + generationCount +
            '}';
    }
}
