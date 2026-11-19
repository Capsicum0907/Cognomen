package io.github.capsicum0907.cognomen.mixin;

import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;

import net.minecraft.client.gui.screens.TitleScreen;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
abstract class TitleScreenMixin {
    @Redirect(
            method = "init",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;realmsNotificationsScreen:Lcom/mojang/realmsclient/gui/screens/RealmsNotificationsScreen;",
                    opcode = Opcodes.PUTFIELD))
    private void cognomen$noRealmsNotifications(TitleScreen screen, RealmsNotificationsScreen notifications) {
    }
}
