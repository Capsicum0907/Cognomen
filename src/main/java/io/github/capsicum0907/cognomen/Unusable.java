package io.github.capsicum0907.cognomen;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

import org.slf4j.Logger;

final class Unusable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final long SHOWN_FOR_MILLIS = 15000L;
    private static final SystemToast.SystemToastId TOAST = new SystemToast.SystemToastId(SHOWN_FOR_MILLIS);

    private static volatile String reported;

    private Unusable() {
    }

    static void report(String name) {
        if (name.equals(reported)) {
            return;
        }
        reported = name;
        LOGGER.error("\"{}\" cannot be used as a player name, so \"{}\" is in use", name, Alias.PLACEHOLDER);
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> SystemToast.addOrUpdate(
                minecraft.getToasts(),
                TOAST,
                Component.translatable("cognomen.toast.unusable.title"),
                name.isEmpty()
                        ? Component.translatable("cognomen.toast.empty.message", Alias.PLACEHOLDER)
                        : Component.translatable("cognomen.toast.unusable.message", name, Alias.PLACEHOLDER)));
    }
}
