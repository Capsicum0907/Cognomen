package io.github.capsicum0907.cognomen;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Notices {
    private static final Logger LOGGER = LogManager.getLogger();
    //? if >=1.20.2
    private static final long SHOWN_FOR_MILLIS = 15000L;
    private static final Map<Topic, String> REPORTED = new ConcurrentHashMap<>();

    enum Topic {
        //? if >=1.20.2 {
        NAME("cognomen.toast.name.title"),
        SKIN("cognomen.toast.skin.title");
        //?} else {
        /*NAME("cognomen.toast.name.title", SystemToast.SystemToastIds.PERIODIC_NOTIFICATION),
        SKIN("cognomen.toast.skin.title", SystemToast.SystemToastIds.PACK_LOAD_FAILURE);
        *///?}

        private final String titleKey;
        //? if >=1.20.2 {
        private final SystemToast.SystemToastId toast = new SystemToast.SystemToastId(SHOWN_FOR_MILLIS);

        Topic(String titleKey) {
            this.titleKey = titleKey;
        }
        //?} else {
        /*private final SystemToast.SystemToastIds toast;

        Topic(String titleKey, SystemToast.SystemToastIds toast) {
            this.titleKey = titleKey;
            this.toast = toast;
        }
        *///?}
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
