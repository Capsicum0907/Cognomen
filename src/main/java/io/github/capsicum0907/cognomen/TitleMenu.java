package io.github.capsicum0907.cognomen;

import com.mojang.logging.LogUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.contents.TranslatableContents;
//? if neoforge {
import net.neoforged.neoforge.client.event.ScreenEvent;
//?} else if forge
/*import net.minecraftforge.client.event.ScreenEvent;*/

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
        event.removeListener(removed);
        List<AbstractWidget> row = widgets.stream()
                .filter(widget -> widget.getY() == removed.getY())
                .toList();
        if (row.size() > 1) {
            widen(row, removed);
            return;
        }
        List<AbstractWidget> below = widgets.stream()
                .filter(widget -> widget.getY() > removed.getY() && !(widget instanceof PlainTextButton))
                .toList();
        int gap = below.stream().mapToInt(widget -> widget.getY() - removed.getY()).min().orElse(0);
        below.forEach(widget -> widget.setY(widget.getY() - gap));
    }

    private static void widen(List<AbstractWidget> row, AbstractWidget removed) {
        List<AbstractWidget> left = row.stream().filter(widget -> widget != removed).toList();
        if (left.size() != 1) {
            return;
        }
        int start = row.stream().mapToInt(AbstractWidget::getX).min().orElseThrow();
        int end = row.stream().mapToInt(widget -> widget.getX() + widget.getWidth()).max().orElseThrow();
        AbstractWidget kept = left.get(0);
        kept.setX(start);
        kept.setWidth(end - start);
    }
}
