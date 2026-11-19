package io.github.capsicum0907.cognomen.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.RealmsMainScreen;

import io.github.capsicum0907.cognomen.Alias;
import io.github.capsicum0907.cognomen.Offline;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Inject(method = "getGameProfile", at = @At("RETURN"), cancellable = true)
    private void cognomen$alias(CallbackInfoReturnable<GameProfile> cir) {
        cir.setReturnValue(Alias.apply(cir.getReturnValue()));
    }

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen cognomen$noMultiplayer(Screen next) {
        if (next instanceof JoinMultiplayerScreen || next instanceof RealmsMainScreen) {
            return Offline.notice();
        }
        return next;
    }
}
