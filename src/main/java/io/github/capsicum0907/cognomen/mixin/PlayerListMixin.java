package io.github.capsicum0907.cognomen.mixin;

import com.mojang.authlib.GameProfile;

//? if >=1.17
import java.util.Optional;
import java.util.UUID;

import io.github.capsicum0907.cognomen.Look;

import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
abstract class PlayerListMixin {
    //? if >=1.17 {
    @Redirect(
            method = "placeNewPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/players/GameProfileCache;get(Ljava/util/UUID;)Ljava/util/Optional;"))
    private Optional<GameProfile> cognomen$noFormerName(GameProfileCache cache, UUID id) {
        if (Look.isAccount(id)) {
            return Optional.empty();
        }
        return cache.get(id);
    }
    //?} else {
    /*@Redirect(
            method = "placeNewPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/players/GameProfileCache;get(Ljava/util/UUID;)Lcom/mojang/authlib/GameProfile;"))
    private GameProfile cognomen$noFormerName(GameProfileCache cache, UUID id) {
        if (Look.isAccount(id)) {
            return null;
        }
        return cache.get(id);
    }
    *///?}
}
