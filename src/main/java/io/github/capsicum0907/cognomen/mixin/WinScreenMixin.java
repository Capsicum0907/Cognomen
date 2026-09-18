package io.github.capsicum0907.cognomen.mixin;

import io.github.capsicum0907.cognomen.Alias;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.server.IntegratedServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WinScreen.class)
abstract class WinScreenMixin {
    @ModifyArg(
            method = "addPoemFile",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"),
            index = 1)
    private String cognomen$poemName(String account) {
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        return server != null ? server.getSingleplayerProfile().getName() : Alias.name(account);
    }
}
