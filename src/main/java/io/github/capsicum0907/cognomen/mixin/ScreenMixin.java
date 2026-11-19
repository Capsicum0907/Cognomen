package io.github.capsicum0907.cognomen.mixin;

import java.util.List;
import java.util.stream.Collectors;

import io.github.capsicum0907.cognomen.TitleMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Later than Fabric API's screen events at the default 1000, so buttons other mods add from them exist by now.
@Mixin(value = Screen.class, priority = 1500)
abstract class ScreenMixin {
    //? if >=1.17 {
    @Shadow
    protected abstract void removeWidget(GuiEventListener listener);
    //?} else {
    /*@Shadow
    protected List<AbstractWidget> buttons;

    @Shadow
    protected List<GuiEventListener> children;
    *///?}

    @Shadow
    public abstract List<? extends GuiEventListener> children();

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At("TAIL"))
    private void cognomen$afterInit(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        withoutOnlinePlay();
    }

    @Inject(method = "resize", at = @At("TAIL"))
    private void cognomen$afterResize(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        withoutOnlinePlay();
    }

    private void withoutOnlinePlay() {
        if (!((Object) this instanceof TitleScreen)) {
            return;
        }
        List<AbstractWidget> widgets = children().stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .collect(Collectors.toList());
        //? if >=1.17 {
        TitleMenu.strip(widgets, this::removeWidget);
        //?} else {
        /*TitleMenu.strip(widgets, widget -> {
            this.buttons.remove(widget);
            this.children.remove(widget);
        });
        *///?}
    }
}
