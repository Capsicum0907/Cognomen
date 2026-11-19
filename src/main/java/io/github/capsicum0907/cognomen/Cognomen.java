package io.github.capsicum0907.cognomen;

//? if neoforge {
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
//?} else if forge && >=1.18 {
/*import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
*///?} else if forge {
/*import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.FMLNetworkConstants;
*///?}

//? if neoforge {
@Mod(value = Cognomen.MODID, dist = Dist.CLIENT)
//?} else if forge
/*@Mod(Cognomen.MODID)*/
public final class Cognomen {
    public static final String MODID = "cognomen";

    //? if neoforge {
    public Cognomen(ModContainer container) {
        Settings.load();
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new SettingsScreen(parent));
        NeoForge.EVENT_BUS.addListener(TitleMenu::withoutOnlinePlay);
    }
    //?} else if forge && >=1.18 {
    /*public Cognomen() {
        if (FMLEnvironment.dist != Dist.CLIENT) {
            return;
        }
        Settings.load();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new SettingsScreen(parent)));
        MinecraftForge.EVENT_BUS.addListener(TitleMenu::withoutOnlinePlay);
    }
    *///?} else if forge {
    /*public Cognomen() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
                () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (remote, fromServer) -> true));
        if (FMLEnvironment.dist != Dist.CLIENT) {
            return;
        }
        Settings.load();
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (minecraft, parent) -> new SettingsScreen(parent));
        MinecraftForge.EVENT_BUS.addListener(TitleMenu::withoutOnlinePlay);
    }
    *///?}
}
