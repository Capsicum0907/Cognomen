package io.github.capsicum0907.cognomen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

public final class Offline {
    private Offline() {
    }

    public static Screen notice() {
        return new AlertScreen(
                () -> Minecraft.getInstance().setScreen(new TitleScreen()),
                Component.translatable("cognomen.multiplayer.title"),
                Component.translatable("cognomen.multiplayer.message"));
    }
}
