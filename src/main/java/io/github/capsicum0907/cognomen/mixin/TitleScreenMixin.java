package io.github.capsicum0907.cognomen.mixin;

import net.minecraft.client.gui.screens.TitleScreen;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//? if >=1.19 {
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
//?} else
/*import net.minecraft.client.gui.screens.Screen;*/

@Mixin(TitleScreen.class)
abstract class TitleScreenMixin {
    //? if >=1.19 {
    @Redirect(
            method = "init",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;realmsNotificationsScreen:Lcom/mojang/realmsclient/gui/screens/RealmsNotificationsScreen;",
                    opcode = Opcodes.PUTFIELD))
    private void cognomen$noRealmsNotifications(TitleScreen screen, RealmsNotificationsScreen notifications) {
    }
    //?} else {
    /*@Redirect(
            method = "init",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;realmsNotificationsScreen:Lnet/minecraft/client/gui/screens/Screen;",
                    opcode = Opcodes.PUTFIELD))
    private void cognomen$noRealmsNotifications(TitleScreen screen, Screen notifications) {
    }
    *///?}
}
