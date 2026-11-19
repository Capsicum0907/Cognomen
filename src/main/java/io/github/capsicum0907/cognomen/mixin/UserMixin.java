package io.github.capsicum0907.cognomen.mixin;

import io.github.capsicum0907.cognomen.Alias;

import net.minecraft.client.User;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <1.20.2
/*import com.mojang.authlib.GameProfile;*/

@Mixin(User.class)
abstract class UserMixin {
    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    private void cognomen$alias(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(Alias.current());
    }
    //? if <1.20.2 {

    /*@Inject(method = "getGameProfile", at = @At("RETURN"), cancellable = true)
    private void cognomen$aliasProfile(CallbackInfoReturnable<GameProfile> cir) {
        cir.setReturnValue(Alias.apply(cir.getReturnValue()));
    }
    *///?}
}
