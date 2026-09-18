package io.github.capsicum0907.cognomen;

import com.mojang.authlib.GameProfile;

import net.minecraft.util.StringUtil;

public final class Alias {
    public static final String PLACEHOLDER = "Steve";
    public static final String SKIN_PROPERTY = "textures";

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
        if (!resolved.equals(configured)) {
            Unusable.report(configured);
        }
        return resolved;
    }

    public static boolean usable(String name) {
        return !name.isEmpty() && StringUtil.isValidPlayerName(name);
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
