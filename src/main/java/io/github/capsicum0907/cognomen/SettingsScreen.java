package io.github.capsicum0907.cognomen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

//? if >=1.20 {
import net.minecraft.client.gui.GuiGraphics;
//?} else
/*import com.mojang.blaze3d.vertex.PoseStack;*/
//? if >=1.19.3 {
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
//?} else {
/*import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.components.AbstractWidget;
*///?}

public final class SettingsScreen extends Screen {
    private static final int FIELD_WIDTH = 240;
    private static final int FIELD_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 116;
    private static final int LABEL_GAP = 12;
    private static final int ROW_GAP = 40;
    private static final int SKIN_PATH_LENGTH = 256;
    private static final int TEXT = 0xFFFFFF;
    private static final int LABEL = 0xA0A0A0;
    private static final int INVALID = 0xFF5555;

    private final Screen parent;
    private EditBox name;
    private String nameText;
    private String skinText;
    private boolean slimArms;
    //? if <1.19.3 {
    /*private static final int TOOLTIP_WIDTH = 200;
    private final List<Hint> hints = new ArrayList<>();

    private static final class Hint {
        private final AbstractWidget widget;
        private final Component text;

        private Hint(AbstractWidget widget, Component text) {
            this.widget = widget;
            this.text = text;
        }
    }
    *///?}

    public SettingsScreen(Screen parent) {
        super(Component.translatable("cognomen.configuration.title"));
        this.parent = parent;
        Settings.Values values = Settings.get();
        this.nameText = values.name;
        this.skinText = values.skin;
        this.slimArms = values.slimArms;
    }

    private int left() {
        return this.width / 2 - FIELD_WIDTH / 2;
    }

    private int top() {
        return this.height / 2 - ROW_GAP * 2;
    }

    @Override
    protected void init() {
        int left = left();
        int top = top();
        //? if <1.19.3
        /*this.hints.clear();*/

        this.name = new EditBox(this.font, left, top + LABEL_GAP, FIELD_WIDTH, FIELD_HEIGHT, label("name"));
        this.name.setMaxLength(Alias.LENGTH);
        this.name.setValue(this.nameText);
        this.name.setResponder(this::nameChanged);
        nameChanged(this.nameText);
        addRenderableWidget(this.name);
        hint(this.name, "name");

        EditBox skin = new EditBox(this.font, left, top + ROW_GAP + LABEL_GAP, FIELD_WIDTH, FIELD_HEIGHT, label("skin"));
        skin.setMaxLength(SKIN_PATH_LENGTH);
        skin.setValue(this.skinText);
        skin.setResponder(value -> this.skinText = value);
        addRenderableWidget(skin);
        hint(skin, "skin");

        int toggle = top + ROW_GAP * 2 + LABEL_GAP;
        //? if >=1.19.3 {
        hint(addRenderableWidget(CycleButton.onOffBuilder(this.slimArms)
                .create(left, toggle, FIELD_WIDTH, FIELD_HEIGHT, label("slimArms"),
                        (button, value) -> this.slimArms = value)), "slimArms");
        //?} else {
        /*hint(addRenderableWidget(button(left, toggle, FIELD_WIDTH, slimArmsLabel(), button -> {
            this.slimArms = !this.slimArms;
            button.setMessage(slimArmsLabel());
        })), "slimArms");
        *///?}

        int buttons = top + ROW_GAP * 3 + LABEL_GAP;
        addRenderableWidget(button(left, buttons, BUTTON_WIDTH, CommonComponents.GUI_DONE, button -> saveAndClose()));
        addRenderableWidget(button(left + FIELD_WIDTH - BUTTON_WIDTH, buttons, BUTTON_WIDTH, CommonComponents.GUI_CANCEL,
                button -> onClose()));

        //? if >=1.19.4 {
        setInitialFocus(this.name);
        //?} else {
        /*setFocused(this.name);
        this.name.setFocus(true);
        *///?}
    }

    private static Button button(int x, int y, int width, Component text, Button.OnPress onPress) {
        //? if >=1.19.3 {
        return Button.builder(text, onPress).bounds(x, y, width, FIELD_HEIGHT).build();
        //?} else
        /*return new Button(x, y, width, FIELD_HEIGHT, text, onPress);*/
    }

    //? if >=1.19.3 {
    private static void hint(net.minecraft.client.gui.components.AbstractWidget widget, String key) {
        widget.setTooltip(Tooltip.create(tooltip(key)));
    }
    //?} else {
    /*private void hint(AbstractWidget widget, String key) {
        this.hints.add(new Hint(widget, tooltip(key)));
    }

    private Component slimArmsLabel() {
        return CommonComponents.optionStatus(label("slimArms"), this.slimArms);
    }
    *///?}

    //? if >=1.20 {
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        //? if <1.20.2
        /*renderBackground(graphics);*/
        super.render(graphics, mouseX, mouseY, partialTick);
        int left = left();
        int top = top();
        graphics.drawCenteredString(this.font, this.title, this.width / 2, top - ROW_GAP / 2, TEXT);
        graphics.drawString(this.font, label("name"), left, top, LABEL);
        graphics.drawString(this.font, label("skin"), left, top + ROW_GAP, LABEL);
    }
    //?} else {
    /*@Override
    public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
        renderBackground(pose);
        super.render(pose, mouseX, mouseY, partialTick);
        int left = left();
        int top = top();
        drawCenteredString(pose, this.font, this.title, this.width / 2, top - ROW_GAP / 2, TEXT);
        drawString(pose, this.font, label("name"), left, top, LABEL);
        drawString(pose, this.font, label("skin"), left, top + ROW_GAP, LABEL);
        for (Hint hint : this.hints) {
            if (hint.widget.isMouseOver(mouseX, mouseY)) {
                renderTooltip(pose, this.font.split(hint.text, TOOLTIP_WIDTH), mouseX, mouseY);
            }
        }
    }
    *///?}

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    private void saveAndClose() {
        Settings.save(new Settings.Values(this.nameText, this.skinText, this.slimArms));
        onClose();
    }

    private void nameChanged(String value) {
        this.nameText = value;
        this.name.setTextColor(Alias.usable(value) ? TEXT : INVALID);
    }

    private static Component label(String key) {
        return Component.translatable("cognomen.configuration." + key);
    }

    private static Component tooltip(String key) {
        return Component.translatable("cognomen.configuration." + key + ".tooltip");
    }
}
