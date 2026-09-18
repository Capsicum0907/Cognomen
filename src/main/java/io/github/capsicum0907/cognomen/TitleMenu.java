package io.github.capsicum0907.cognomen;

import com.mojang.logging.LogUtils;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.client.event.ScreenEvent;

import org.slf4j.Logger;

final class TitleMenu {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String MULTIPLAYER_KEY = "menu.multiplayer";

    private TitleMenu() {
    }

    static void withoutMultiplayer(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen)) {
            return;
        }
        List<AbstractWidget> widgets = event.getListenersList().stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .toList();
        Optional<AbstractWidget> multiplayer = widgets.stream()
                .filter(widget -> widget.getMessage().getContents() instanceof TranslatableContents contents
                        && contents.getKey().equals(MULTIPLAYER_KEY))
                .findFirst();
        if (multiplayer.isEmpty()) {
            LOGGER.warn("The title screen has no multiplayer button to remove");
            return;
        }
        AbstractWidget removed = multiplayer.get();
        List<AbstractWidget> below = widgets.stream()
                .filter(widget -> widget.getY() > removed.getY() && !(widget instanceof PlainTextButton))
                .toList();
        int row = below.stream().mapToInt(widget -> widget.getY() - removed.getY()).min().orElse(0);
        event.removeListener(removed);
        below.forEach(widget -> widget.setY(widget.getY() - row));
    }
}
