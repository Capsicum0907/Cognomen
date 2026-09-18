package io.github.capsicum0907.cognomen;

import com.mojang.logging.LogUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.client.event.ScreenEvent;

import org.slf4j.Logger;

final class TitleMenu {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Set<String> ONLINE_KEYS = Set.of("menu.multiplayer", "menu.online");

    private TitleMenu() {
    }

    static void withoutOnlinePlay(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen)) {
            return;
        }
        for (String key : ONLINE_KEYS) {
            remove(event, key);
        }
    }

    private static void remove(ScreenEvent.Init.Post event, String key) {
        List<AbstractWidget> widgets = event.getListenersList().stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .toList();
        Optional<AbstractWidget> found = widgets.stream()
                .filter(widget -> widget.getMessage().getContents() instanceof TranslatableContents contents
                        && contents.getKey().equals(key))
                .findFirst();
        if (found.isEmpty()) {
            LOGGER.warn("The title screen has no \"{}\" button to remove", key);
            return;
        }
        AbstractWidget removed = found.get();
        List<AbstractWidget> below = widgets.stream()
                .filter(widget -> widget.getY() > removed.getY() && !(widget instanceof PlainTextButton))
                .toList();
        int row = below.stream().mapToInt(widget -> widget.getY() - removed.getY()).min().orElse(0);
        event.removeListener(removed);
        below.forEach(widget -> widget.setY(widget.getY() - row));
    }
}
