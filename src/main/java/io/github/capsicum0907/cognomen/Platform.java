package io.github.capsicum0907.cognomen;

import java.nio.file.Path;

import net.neoforged.fml.loading.FMLPaths;

public final class Platform {
    private Platform() {
    }

    public static Path gameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
