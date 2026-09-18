package io.github.capsicum0907.cognomen.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Shadow
    private IntegratedServer singleplayerServer;

    @ModifyArg(
            method = "doWorldLoad",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/login/ServerboundHelloPacket;<init>(Ljava/lang/String;Ljava/util/UUID;)V"),
            index = 0)
    private String cognomen$helloAsTheServerKnowsUs(String account) {
        return this.singleplayerServer.getSingleplayerProfile().getName();
    }
}
