package io.github.capsicum0907.cognomen.mixin;

import io.github.capsicum0907.cognomen.Offline;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
//? if >=1.20.5
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
abstract class ConnectScreenMixin {
    @Inject(method = "startConnecting", at = @At("HEAD"), cancellable = true)
    private static void cognomen$noMultiplayer(
            Screen parent, Minecraft minecraft, ServerAddress address, ServerData data,
            //? if >=1.20.5 {
            boolean quickPlay, TransferState transfer, CallbackInfo ci) {
            //?} else if >=1.20 {
            /*boolean quickPlay, CallbackInfo ci) {
            *///?} else
            /*CallbackInfo ci) {*/
        minecraft.setScreen(Offline.notice());
        ci.cancel();
    }
}
