package io.github.capsicum0907.cognomen;

import com.mojang.authlib.GameProfile;

import java.util.Optional;

import net.minecraft.util.StringUtil;

public final class Alias {
    public static final String PLACEHOLDER = "player";

    private Alias() {
    }

    public static GameProfile apply(GameProfile account) {
        return chosen().map(name -> rename(account, name)).orElse(account);
    }

    public static String name(String account) {
        return chosen().orElse(account);
    }

    public static boolean usable(String name) {
        return !name.isEmpty() && StringUtil.isValidPlayerName(name);
    }

    public static Optional<String> resolve(String configured) {
        if (configured.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(usable(configured) ? configured : PLACEHOLDER);
    }

    public static GameProfile rename(GameProfile account, String name) {
        GameProfile renamed = new GameProfile(account.getId(), name);
        renamed.getProperties().putAll(account.getProperties());
        return renamed;
    }

    private static Optional<String> chosen() {
        String configured = CognomenConfig.NAME.get();
        Optional<String> resolved = resolve(configured);
        if (resolved.isPresent() && !resolved.get().equals(configured)) {
            Unusable.report(configured);
        }
        return resolved;
    }
}
