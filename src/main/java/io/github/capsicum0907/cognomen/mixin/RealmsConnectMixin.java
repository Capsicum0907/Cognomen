package io.github.capsicum0907.cognomen.mixin;

import com.mojang.realmsclient.dto.RealmsServer;

import io.github.capsicum0907.cognomen.Offline;

import net.minecraft.client.Minecraft;
//? if >=1.17
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.realms.RealmsConnect;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RealmsConnect.class)
abstract class RealmsConnectMixin {
    @Inject(method = "connect", at = @At("HEAD"), cancellable = true)
    //? if >=1.17 {
    private void cognomen$noRealms(RealmsServer server, ServerAddress address, CallbackInfo ci) {
    //?} else
    /*private void cognomen$noRealms(RealmsServer server, String host, int port, CallbackInfo ci) {*/
        Minecraft.getInstance().setScreen(Offline.notice());
        ci.cancel();
    }
}
