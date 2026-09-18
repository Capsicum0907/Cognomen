package io.github.capsicum0907.cognomen.mixin;

import com.mojang.authlib.GameProfile;

import io.github.capsicum0907.cognomen.Alias;

import net.minecraft.client.server.IntegratedServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(IntegratedServer.class)
abstract class IntegratedServerMixin {
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "setSingleplayerProfile(Lcom/mojang/authlib/GameProfile;)V"))
    private GameProfile cognomen$alias(GameProfile account) {
        return Alias.apply(account);
    }
}
