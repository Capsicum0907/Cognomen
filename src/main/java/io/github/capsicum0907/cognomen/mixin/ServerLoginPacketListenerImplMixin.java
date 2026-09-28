package io.github.capsicum0907.cognomen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import io.github.capsicum0907.cognomen.Alias;

import net.minecraft.server.network.ServerLoginPacketListenerImpl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLoginPacketListenerImpl.class)
abstract class ServerLoginPacketListenerImplMixin {
    @WrapOperation(
            method = "handleHello",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/StringUtil;isValidPlayerName(Ljava/lang/String;)Z"))
    private boolean cognomen$aliasPassesTheLogin(String name, Operation<Boolean> original) {
        return Alias.usable(name);
    }
}
