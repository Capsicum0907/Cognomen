package io.github.capsicum0907.cognomen;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class CognomenConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> NAME = BUILDER
            .define("name", Alias.PLACEHOLDER);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private CognomenConfig() {
    }
}
