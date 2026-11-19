package io.github.capsicum0907.cognomen.mixin;

//? if >=1.20.2 {
import com.mojang.authlib.GameProfile;
//?} else
/*import com.mojang.authlib.properties.PropertyMap;*/
import com.mojang.realmsclient.RealmsMainScreen;

//? if >=1.20.2
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
    //? if >=1.20.2 {
    @Inject(method = "getGameProfile", at = @At("RETURN"), cancellable = true)
    private void cognomen$alias(CallbackInfoReturnable<GameProfile> cir) {
        cir.setReturnValue(Alias.apply(cir.getReturnValue()));
    }
    //?} else {
    /*@Inject(method = "getProfileProperties", at = @At("HEAD"), cancellable = true)
    private void cognomen$noAccountSkin(CallbackInfoReturnable<PropertyMap> cir) {
        cir.setReturnValue(new PropertyMap());
    }
    *///?}

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen cognomen$noMultiplayer(Screen next) {
        //? if >=1.17 {
        if (next instanceof JoinMultiplayerScreen || next instanceof RealmsMainScreen) {
        //?} else
        /*if (next instanceof JoinMultiplayerScreen || next instanceof RealmsMainScreen || next instanceof net.minecraft.client.gui.screens.ConnectScreen) {*/
            return Offline.notice();
        }
        return next;
    }
}
