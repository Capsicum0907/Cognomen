package io.github.capsicum0907.cognomen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

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

        this.name = new EditBox(this.font, left, top + LABEL_GAP, FIELD_WIDTH, FIELD_HEIGHT, label("name"));
        this.name.setMaxLength(Alias.LENGTH);
        this.name.setValue(this.nameText);
        this.name.setResponder(this::nameChanged);
        this.name.setTooltip(Tooltip.create(tooltip("name")));
        nameChanged(this.nameText);
        addRenderableWidget(this.name);

        EditBox skin = new EditBox(this.font, left, top + ROW_GAP + LABEL_GAP, FIELD_WIDTH, FIELD_HEIGHT, label("skin"));
        skin.setMaxLength(SKIN_PATH_LENGTH);
        skin.setValue(this.skinText);
        skin.setResponder(value -> this.skinText = value);
        skin.setTooltip(Tooltip.create(tooltip("skin")));
        addRenderableWidget(skin);

        addRenderableWidget(CycleButton.onOffBuilder(this.slimArms)
                .withTooltip(value -> Tooltip.create(tooltip("slimArms")))
                .create(left, top + ROW_GAP * 2 + LABEL_GAP, FIELD_WIDTH, FIELD_HEIGHT, label("slimArms"),
                        (button, value) -> this.slimArms = value));

        int buttons = top + ROW_GAP * 3 + LABEL_GAP;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> saveAndClose())
                .bounds(left, buttons, BUTTON_WIDTH, FIELD_HEIGHT).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
                .bounds(left + FIELD_WIDTH - BUTTON_WIDTH, buttons, BUTTON_WIDTH, FIELD_HEIGHT).build());

        setInitialFocus(this.name);
    }

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
