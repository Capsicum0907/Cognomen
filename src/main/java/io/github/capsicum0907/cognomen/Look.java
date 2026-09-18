package io.github.capsicum0907.cognomen;

import com.mojang.blaze3d.platform.NativeImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

public final class Look {
    public static final int SKIN_SIZE = 64;

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Cognomen.MODID, "skin");
    private static volatile Loaded loaded;

    private record Loaded(Path path, FileTime modified, boolean slim, PlayerSkin skin) {
        boolean matches(Path path, FileTime modified, boolean slim) {
            return this.path.equals(path) && this.modified.equals(modified) && this.slim == slim;
        }
    }

    private Look() {
    }

    public static boolean fits(int width, int height) {
        return width == SKIN_SIZE && height == SKIN_SIZE;
    }

    public static PlayerSkin forAccount(PlayerSkin steve) {
        if (!CognomenConfig.SPEC.isLoaded()) {
            return steve;
        }
        String configured = CognomenConfig.SKIN.get();
        if (configured.isEmpty()) {
            Notices.settle(Notices.Topic.SKIN);
            return steve;
        }
        boolean slim = CognomenConfig.SLIM_ARMS.get();
        try {
            Path path = FMLPaths.GAMEDIR.get().resolve(configured);
            FileTime modified = Files.getLastModifiedTime(path);
            Loaded current = loaded;
            if (current != null && current.matches(path, modified, slim)) {
                return current.skin();
            }
            NativeImage image;
            try (InputStream in = Files.newInputStream(path)) {
                image = NativeImage.read(in);
            }
            if (!fits(image.getWidth(), image.getHeight())) {
                Notices.report(Notices.Topic.SKIN, configured + " size",
                        Component.translatable("cognomen.toast.skin.size",
                                configured, image.getWidth(), image.getHeight(), SKIN_SIZE, SKIN_SIZE));
                image.close();
                return steve;
            }
            Minecraft.getInstance().getTextureManager().register(TEXTURE, new DynamicTexture(image));
            PlayerSkin skin = new PlayerSkin(
                    TEXTURE, null, null, null, slim ? PlayerSkin.Model.SLIM : PlayerSkin.Model.WIDE, true);
            loaded = new Loaded(path, modified, slim, skin);
            Notices.settle(Notices.Topic.SKIN);
            return skin;
        } catch (NoSuchFileException | InvalidPathException missing) {
            Notices.report(Notices.Topic.SKIN, configured + " missing",
                    Component.translatable("cognomen.toast.skin.missing", configured));
        } catch (IOException unreadable) {
            Notices.report(Notices.Topic.SKIN, configured + " unreadable",
                    Component.translatable("cognomen.toast.skin.unreadable", configured));
        }
        return steve;
    }
}
