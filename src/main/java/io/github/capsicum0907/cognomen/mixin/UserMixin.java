package io.github.capsicum0907.cognomen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import io.github.capsicum0907.cognomen.Alias;

import net.minecraft.client.User;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(User.class)
abstract class UserMixin {
    @ModifyReturnValue(method = "getName", at = @At("RETURN"))
    private String cognomen$alias(String account) {
        return Alias.current();
    }
}
