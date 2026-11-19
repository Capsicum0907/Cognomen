package io.github.capsicum0907.cognomen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.TitleScreen;
//? if >=1.19 {
import net.minecraft.network.chat.contents.TranslatableContents;
//?} else
/*import net.minecraft.network.chat.TranslatableComponent;*/
//? if >=1.18
import net.minecraft.client.gui.components.PlainTextButton;
//? if neoforge {
import net.neoforged.neoforge.client.event.ScreenEvent;
//?} else if forge && >=1.18 {
/*import net.minecraftforge.client.event.ScreenEvent;
*///?} else if forge
/*import net.minecraftforge.client.event.GuiScreenEvent;*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class TitleMenu {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<String> ONLINE_KEYS = Arrays.asList("menu.multiplayer", "menu.online");

    private TitleMenu() {
    }

    //? if !fabric && >=1.18 {
    static void withoutOnlinePlay(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen)) {
            return;
        }
        List<AbstractWidget> widgets = event.getListenersList().stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .collect(Collectors.toList());
        strip(widgets, event::removeListener);
    }
    //?} else if !fabric {
    /*static void withoutOnlinePlay(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.getGui() instanceof TitleScreen)) {
            return;
        }
        strip(new ArrayList<>(event.getWidgetList()), event::removeWidget);
    }
    *///?}

    public static void strip(List<AbstractWidget> widgets, Consumer<AbstractWidget> removal) {
        List<AbstractWidget> remaining = new ArrayList<>(widgets);
        for (String key : ONLINE_KEYS) {
            remove(remaining, removal, key);
        }
    }

    private static void remove(List<AbstractWidget> widgets, Consumer<AbstractWidget> removal, String key) {
        AbstractWidget removed = widgets.stream()
                .filter(widget -> key.equals(key(widget)))
                .findFirst()
                .orElse(null);
        if (removed == null) {
            LOGGER.warn("The title screen has no \"{}\" button to remove", key);
            return;
        }
        removal.accept(removed);
        List<AbstractWidget> row = widgets.stream()
                .filter(widget -> y(widget) == y(removed))
                .collect(Collectors.toList());
        widgets.remove(removed);
        if (row.size() > 1) {
            widen(row, removed);
            return;
        }
        List<AbstractWidget> below = widgets.stream()
                .filter(widget -> y(widget) > y(removed) && !isCopyright(widget))
                .collect(Collectors.toList());
        int gap = below.stream().mapToInt(widget -> y(widget) - y(removed)).min().orElse(0);
        below.forEach(widget -> setY(widget, y(widget) - gap));
    }

    private static void widen(List<AbstractWidget> row, AbstractWidget removed) {
        List<AbstractWidget> left = row.stream().filter(widget -> widget != removed).collect(Collectors.toList());
        if (left.size() != 1) {
            return;
        }
        int start = row.stream().mapToInt(TitleMenu::x).min().getAsInt();
        int end = row.stream().mapToInt(widget -> x(widget) + widget.getWidth()).max().getAsInt();
        AbstractWidget kept = left.get(0);
        setX(kept, start);
        kept.setWidth(end - start);
    }

    private static boolean isCopyright(AbstractWidget widget) {
        //? if >=1.18 {
        return widget instanceof PlainTextButton;
        //?} else
        /*return false;*/
    }

    private static String key(AbstractWidget widget) {
        //? if >=1.19 {
        return widget.getMessage().getContents() instanceof TranslatableContents contents ? contents.getKey() : null;
        //?} else
        /*return widget.getMessage() instanceof TranslatableComponent component ? component.getKey() : null;*/
    }

    //? if >=1.19.3 {
    private static int x(AbstractWidget widget) {
        return widget.getX();
    }

    private static int y(AbstractWidget widget) {
        return widget.getY();
    }

    private static void setX(AbstractWidget widget, int x) {
        widget.setX(x);
    }

    private static void setY(AbstractWidget widget, int y) {
        widget.setY(y);
    }
    //?} else {
    /*private static int x(AbstractWidget widget) {
        return widget.x;
    }

    private static int y(AbstractWidget widget) {
        return widget.y;
    }

    private static void setX(AbstractWidget widget, int x) {
        widget.x = x;
    }

    private static void setY(AbstractWidget widget, int y) {
        widget.y = y;
    }
    *///?}
}
