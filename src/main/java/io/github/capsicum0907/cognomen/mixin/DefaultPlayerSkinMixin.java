package io.github.capsicum0907.cognomen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;

import java.util.Arrays;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DefaultPlayerSkin.class)
abstract class DefaultPlayerSkinMixin {
    @Shadow
    @Final
    private static PlayerSkin[] DEFAULT_SKINS;

    @ModifyReturnValue(method = "get(Ljava/util/UUID;)Lnet/minecraft/client/resources/PlayerSkin;", at = @At("RETURN"))
    private static PlayerSkin cognomen$steveForTheAccount(PlayerSkin byHash, @Local(argsOnly = true) UUID id) {
        if (!id.equals(Minecraft.getInstance().getUser().getProfileId())) {
            return byHash;
        }
        return Arrays.stream(DEFAULT_SKINS)
                .filter(skin -> skin.texture().equals(DefaultPlayerSkin.getDefaultTexture()))
                .findFirst()
                .orElse(byHash);
    }
}
