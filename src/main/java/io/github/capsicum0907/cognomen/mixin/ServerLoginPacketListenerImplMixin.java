package io.github.capsicum0907.cognomen.mixin;

import io.github.capsicum0907.cognomen.Alias;

import net.minecraft.server.network.ServerLoginPacketListenerImpl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLoginPacketListenerImpl.class)
abstract class ServerLoginPacketListenerImplMixin {
    //? if >=1.18 {
    //? if >=1.20.2 {
    @Redirect(
            method = "handleHello",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/StringUtil;isValidPlayerName(Ljava/lang/String;)Z"))
    //?} else {
    /*@Redirect(
            method = "handleHello",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerLoginPacketListenerImpl;isValidUsername(Ljava/lang/String;)Z"))
    *///?}
    private boolean cognomen$aliasPassesTheLogin(String name) {
        return Alias.usable(name);
    }
    //?}
}
