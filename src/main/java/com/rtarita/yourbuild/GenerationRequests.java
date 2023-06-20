package com.rtarita.yourbuild;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.generator.GenerationRequest;
import com.rtarita.yourbuild.model.BuildModelSchema;
import com.rtarita.yourbuild.model.CaseModelSchema;
import com.rtarita.yourbuild.model.ClientModelSchema;
import com.rtarita.yourbuild.model.ClientOrderModelSchema;
import com.rtarita.yourbuild.model.CpuCoolerModelSchema;
import com.rtarita.yourbuild.model.CpuModelSchema;
import com.rtarita.yourbuild.model.CpuSocketTypeModelSchema;
import com.rtarita.yourbuild.model.GpuModelSchema;
import com.rtarita.yourbuild.model.MainboardCompatibilityModelSchema;
import com.rtarita.yourbuild.model.MainboardFormFactorModelSchema;
import com.rtarita.yourbuild.model.MainboardModelSchema;
import com.rtarita.yourbuild.model.ManufacturerModelSchema;
import com.rtarita.yourbuild.model.PartModelSchema;
import com.rtarita.yourbuild.model.PermanentStorageModelSchema;
import com.rtarita.yourbuild.model.ProducesModelSchema;
import com.rtarita.yourbuild.model.PsuModelSchema;
import com.rtarita.yourbuild.model.RamModelSchema;
import com.rtarita.yourbuild.model.RequiresFromModelSchema;
import com.rtarita.yourbuild.model.VendorModelSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class GenerationRequests {
    private GenerationRequests() {
    }

    private static Faker nextFaker(Random superRandom) {
        return new Faker(new Random(superRandom.nextInt()));
    }

    public static List<GenerationRequest<?, ?>> getRequests(int superSeed) {
        Random superRandom = new Random(superSeed);
        List<GenerationRequest<?, ?>> result = new ArrayList<>(19);

        result.add(
            GenerationRequest.builder(new BuildModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(4000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new CaseModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new ClientModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new ClientOrderModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new CpuCoolerModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new CpuModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new CpuSocketTypeModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new GpuModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new MainboardCompatibilityModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new MainboardFormFactorModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new MainboardModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new ManufacturerModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new PartModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(8000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new PermanentStorageModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new ProducesModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(4000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new PsuModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new RamModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(1000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new RequiresFromModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        result.add(
            GenerationRequest.builder(new VendorModelSchema())
                .faker(nextFaker(superRandom))
                .generationCount(2000)
                .build()
        );

        return result;
    }
}
