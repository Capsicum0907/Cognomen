package io.github.capsicum0907.cognomen.mixin;

import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;

import net.minecraft.client.gui.screens.TitleScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
abstract class TitleScreenMixin {
    @Redirect(
            method = "init",
            at = @At(value = "NEW", target = "()Lcom/mojang/realmsclient/gui/screens/RealmsNotificationsScreen;"))
    private RealmsNotificationsScreen cognomen$noRealmsNotifications() {
        return null;
    }
}
