package io.github.capsicum0907.cognomen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Cognomen.MODID, dist = Dist.CLIENT)
public final class Cognomen {
    public static final String MODID = "cognomen";

    public Cognomen(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, CognomenConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
