package io.github.capsicum0907.cognomen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Cognomen.MODID, dist = Dist.CLIENT)
public final class Cognomen {
    public static final String MODID = "cognomen";

    public Cognomen(ModContainer container) {
        Settings.load();
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new SettingsScreen(parent));
        NeoForge.EVENT_BUS.addListener(TitleMenu::withoutOnlinePlay);
    }
}
