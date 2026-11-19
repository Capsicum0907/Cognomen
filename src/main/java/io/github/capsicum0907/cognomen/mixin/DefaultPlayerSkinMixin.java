package io.github.capsicum0907.cognomen.mixin;

import java.util.Arrays;
import java.util.UUID;

import io.github.capsicum0907.cognomen.Look;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultPlayerSkin.class)
abstract class DefaultPlayerSkinMixin {
    @Shadow
    @Final
    private static PlayerSkin[] DEFAULT_SKINS;

    @Inject(method = "get(Ljava/util/UUID;)Lnet/minecraft/client/resources/PlayerSkin;",
            at = @At("RETURN"), cancellable = true)
    private static void cognomen$steveForTheAccount(UUID id, CallbackInfoReturnable<PlayerSkin> cir) {
        if (!id.equals(Minecraft.getInstance().getUser().getProfileId())) {
            return;
        }
        PlayerSkin byHash = cir.getReturnValue();
        cir.setReturnValue(Look.forAccount(Arrays.stream(DEFAULT_SKINS)
                .filter(skin -> skin.texture().equals(DefaultPlayerSkin.getDefaultTexture()))
                .findFirst()
                .orElse(byHash)));
    }
}
