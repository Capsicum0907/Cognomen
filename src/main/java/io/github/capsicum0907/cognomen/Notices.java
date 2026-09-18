package io.github.capsicum0907.cognomen;

import com.mojang.logging.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

import org.slf4j.Logger;

final class Notices {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final long SHOWN_FOR_MILLIS = 15000L;
    private static final Map<Topic, String> REPORTED = new ConcurrentHashMap<>();

    enum Topic {
        NAME("cognomen.toast.name.title"),
        SKIN("cognomen.toast.skin.title");

        private final String titleKey;
        private final SystemToast.SystemToastId toast = new SystemToast.SystemToastId(SHOWN_FOR_MILLIS);

        Topic(String titleKey) {
            this.titleKey = titleKey;
        }
    }

    private Notices() {
    }

    static void report(Topic topic, String cause, Component message) {
        if (cause.equals(REPORTED.put(topic, cause))) {
            return;
        }
        LOGGER.error("{}", message.getString());
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> SystemToast.addOrUpdate(
                minecraft.getToasts(), topic.toast, Component.translatable(topic.titleKey), message));
    }

    static void settle(Topic topic) {
        REPORTED.remove(topic);
    }
}
