package io.github.capsicum0907.cognomen;

import com.mojang.authlib.GameProfile;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;

public final class Alias {
    public static final String PLACEHOLDER = "Steve";
    public static final String SKIN_PROPERTY = "textures";
    public static final int LENGTH = SharedConstants.MAX_PLAYER_NAME_LENGTH;

    private Alias() {
    }

    public static GameProfile apply(GameProfile account) {
        return rename(account, current());
    }

    public static String current() {
        if (!CognomenConfig.SPEC.isLoaded()) {
            return PLACEHOLDER;
        }
        String configured = CognomenConfig.NAME.get();
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
                || StringUtil.isWhitespace(character);
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
