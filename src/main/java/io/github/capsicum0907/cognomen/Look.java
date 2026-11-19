package io.github.capsicum0907.cognomen;

import com.mojang.blaze3d.platform.NativeImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class Look {
    public static final int SKIN_SIZE = 64;

    //? if >=1.21 {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Cognomen.MODID, "skin");
    //?} else
    /*private static final ResourceLocation TEXTURE = new ResourceLocation(Cognomen.MODID, "skin");*/
    private static volatile Loaded loaded;

    public static final class Custom {
        public final ResourceLocation texture;
        public final boolean slim;

        private Custom(ResourceLocation texture, boolean slim) {
            this.texture = texture;
            this.slim = slim;
        }
    }

    private static final class Loaded {
        private final Path path;
        private final FileTime modified;
        private final Custom custom;

        private Loaded(Path path, FileTime modified, Custom custom) {
            this.path = path;
            this.modified = modified;
            this.custom = custom;
        }

        private boolean matches(Path path, FileTime modified, boolean slim) {
            return this.path.equals(path) && this.modified.equals(modified) && this.custom.slim == slim;
        }
    }

    private Look() {
    }

    public static boolean fits(int width, int height) {
        return width == SKIN_SIZE && height == SKIN_SIZE;
    }

    public static boolean isAccount(UUID id) {
        return id.equals(Minecraft.getInstance().getUser().getProfileId());
    }

    @Nullable
    public static Custom custom() {
        if (!Settings.loaded()) {
            return null;
        }
        String configured = Settings.get().skin;
        if (configured.isEmpty()) {
            Notices.settle(Notices.Topic.SKIN);
            return null;
        }
        boolean slim = Settings.get().slimArms;
        try {
            Path path = Platform.gameDir().resolve(configured);
            FileTime modified = Files.getLastModifiedTime(path);
            Loaded current = loaded;
            if (current != null && current.matches(path, modified, slim)) {
                return current.custom;
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
                return null;
            }
            Minecraft.getInstance().getTextureManager().register(TEXTURE, new DynamicTexture(image));
            Custom custom = new Custom(TEXTURE, slim);
            loaded = new Loaded(path, modified, custom);
            Notices.settle(Notices.Topic.SKIN);
            return custom;
        } catch (NoSuchFileException | InvalidPathException missing) {
            Notices.report(Notices.Topic.SKIN, configured + " missing",
                    Component.translatable("cognomen.toast.skin.missing", configured));
        } catch (IOException unreadable) {
            Notices.report(Notices.Topic.SKIN, configured + " unreadable",
                    Component.translatable("cognomen.toast.skin.unreadable", configured));
        }
        return null;
    }
}
