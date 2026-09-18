package io.github.capsicum0907.cognomen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;

import net.minecraft.client.gui.screens.TitleScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TitleScreen.class)
abstract class TitleScreenMixin {
    @WrapOperation(
            method = "init",
            at = @At(value = "NEW", target = "()Lcom/mojang/realmsclient/gui/screens/RealmsNotificationsScreen;"))
    private RealmsNotificationsScreen cognomen$noRealmsNotifications(Operation<RealmsNotificationsScreen> original) {
        return null;
    }
}
