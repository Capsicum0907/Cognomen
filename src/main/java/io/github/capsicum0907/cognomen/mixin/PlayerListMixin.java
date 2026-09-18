package io.github.capsicum0907.cognomen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerList.class)
abstract class PlayerListMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @WrapOperation(
            method = "placeNewPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/players/GameProfileCache;get(Ljava/util/UUID;)Ljava/util/Optional;"))
    private Optional<GameProfile> cognomen$noFormerName(
            GameProfileCache cache, UUID id, Operation<Optional<GameProfile>> original,
            @Local(argsOnly = true) ServerPlayer player) {
        if (this.server.isSingleplayerOwner(player.getGameProfile())) {
            return Optional.empty();
        }
        return original.call(cache, id);
    }
}
