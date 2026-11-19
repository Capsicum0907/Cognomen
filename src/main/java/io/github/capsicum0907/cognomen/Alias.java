package io.github.capsicum0907.cognomen;

import com.mojang.authlib.GameProfile;

import net.minecraft.ChatFormatting;
//? if >=1.20.2
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;

public final class Alias {
    public static final String PLACEHOLDER = "Steve";
    public static final String SKIN_PROPERTY = "textures";
    //? if >=1.20.2 {
    public static final int LENGTH = SharedConstants.MAX_PLAYER_NAME_LENGTH;
    //?} else
    /*public static final int LENGTH = 16;*/

    private Alias() {
    }

    public static GameProfile apply(GameProfile account) {
        return rename(account, current());
    }

    public static String current() {
        if (!Settings.loaded()) {
            return PLACEHOLDER;
        }
        String configured = Settings.get().name;
        String resolved = resolve(configured);
        if (resolved.equals(configured)) {
            Notices.settle(Notices.Topic.NAME);
        } else {
            Notices.report(Notices.Topic.NAME, configured, configured.isEmpty()
                    ? Component.translatable("cognomen.toast.empty.message", PLACEHOLDER)
                    : Component.translatable("cognomen.toast.unusable.message", configured, PLACEHOLDER));
        }
        return resolved;
    }

    public static boolean usable(String name) {
        return !name.isEmpty() && name.length() <= LENGTH && name.codePoints().noneMatch(Alias::unusable);
    }

    private static boolean unusable(int character) {
        return character == ChatFormatting.PREFIX_CODE
                || Character.isISOControl(character)
                || Character.isWhitespace(character)
                || Character.isSpaceChar(character);
    }

    public static String resolve(String configured) {
        return usable(configured) ? configured : PLACEHOLDER;
    }

    public static GameProfile rename(GameProfile account, String name) {
        GameProfile renamed = new GameProfile(account.getId(), name);
        account.getProperties().entries().stream()
                .filter(entry -> !entry.getKey().equals(SKIN_PROPERTY))
                .forEach(entry -> renamed.getProperties().put(entry.getKey(), entry.getValue()));
        return renamed;
    }
}
