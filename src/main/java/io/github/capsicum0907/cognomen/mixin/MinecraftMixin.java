package io.github.capsicum0907.cognomen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.RealmsMainScreen;

import io.github.capsicum0907.cognomen.Alias;
import io.github.capsicum0907.cognomen.Offline;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Shadow
    public Screen screen;

    @ModifyReturnValue(method = "getGameProfile", at = @At("RETURN"))
    private GameProfile cognomen$alias(GameProfile account) {
        return Alias.apply(account);
    }

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen cognomen$noMultiplayer(Screen next) {
        if (next instanceof JoinMultiplayerScreen || next instanceof RealmsMainScreen) {
            return Offline.notice(this.screen);
        }
        return next;
    }
}
