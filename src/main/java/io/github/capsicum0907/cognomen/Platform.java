package io.github.capsicum0907.cognomen;

import java.nio.file.Path;

//? if neoforge {
import net.neoforged.fml.loading.FMLPaths;
//?} else if forge {
/*import net.minecraftforge.fml.loading.FMLPaths;
*///?} else if fabric
/*import net.fabricmc.loader.api.FabricLoader;*/

public final class Platform {
    private Platform() {
    }

    //? if fabric {
    /*public static Path gameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
    *///?} else {
    public static Path gameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }
    //?}
}
