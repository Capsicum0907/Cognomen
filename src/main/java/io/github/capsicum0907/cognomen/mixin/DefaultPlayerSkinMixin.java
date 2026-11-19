package io.github.capsicum0907.cognomen.mixin;

import java.util.UUID;

import io.github.capsicum0907.cognomen.Look;

import net.minecraft.client.resources.DefaultPlayerSkin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.20.2 {
import java.util.Arrays;

import net.minecraft.client.resources.PlayerSkin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}

@Mixin(DefaultPlayerSkin.class)
abstract class DefaultPlayerSkinMixin {
    //? if <1.19.3 {
    /*private static final ResourceLocation WIDE_STEVE = DefaultPlayerSkin.getDefaultSkin();
    *///?} else if <1.20.2 {
    /*private static final ResourceLocation WIDE_STEVE = new ResourceLocation("textures/entity/player/wide/steve.png");
    *///?}

    //? if >=1.20.2 {
    @Shadow
    @Final
    private static PlayerSkin[] DEFAULT_SKINS;

    @Inject(method = "get(Ljava/util/UUID;)Lnet/minecraft/client/resources/PlayerSkin;",
            at = @At("RETURN"), cancellable = true)
    private static void cognomen$steveForTheAccount(UUID id, CallbackInfoReturnable<PlayerSkin> cir) {
        if (!Look.isAccount(id)) {
            return;
        }
        Look.Custom custom = Look.custom();
        if (custom != null) {
            cir.setReturnValue(new PlayerSkin(custom.texture, null, null, null,
                    custom.slim ? PlayerSkin.Model.SLIM : PlayerSkin.Model.WIDE, true));
            return;
        }
        PlayerSkin byHash = cir.getReturnValue();
        cir.setReturnValue(Arrays.stream(DEFAULT_SKINS)
                .filter(skin -> skin.texture().equals(DefaultPlayerSkin.getDefaultTexture()))
                .findFirst()
                .orElse(byHash));
    }
    //?} else {
    /*private static final String WIDE = "default";
    private static final String SLIM = "slim";

    @Inject(method = "getDefaultSkin(Ljava/util/UUID;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("RETURN"), cancellable = true)
    private static void cognomen$steveForTheAccount(UUID id, CallbackInfoReturnable<ResourceLocation> cir) {
        if (Look.isAccount(id)) {
            Look.Custom custom = Look.custom();
            cir.setReturnValue(custom != null ? custom.texture : WIDE_STEVE);
        }
    }

    @Inject(method = "getSkinModelName(Ljava/util/UUID;)Ljava/lang/String;", at = @At("RETURN"), cancellable = true)
    private static void cognomen$wideForTheAccount(UUID id, CallbackInfoReturnable<String> cir) {
        if (Look.isAccount(id)) {
            Look.Custom custom = Look.custom();
            cir.setReturnValue(custom != null && custom.slim ? SLIM : WIDE);
        }
    }
    *///?}
}
