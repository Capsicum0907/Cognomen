package io.github.capsicum0907.cognomen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.UUID;

import net.minecraft.ChatFormatting;
//? if >=1.20.2 {
import net.minecraft.util.StringUtil;
//?} else
/*import net.minecraft.server.network.ServerLoginPacketListenerImpl;*/

import org.junit.jupiter.api.Test;

class AliasTest {
    private static final UUID ID = UUID.fromString("00000000-0000-4000-8000-000000000001");

    private static GameProfile account() {
        GameProfile profile = new GameProfile(ID, "AccountName");
        profile.getProperties().put("textures", new Property(Alias.SKIN_PROPERTY, "payload", "signature"));
        return profile;
    }

    @Test
    void renameKeepsTheIdSoTheSaveStillBelongsToThePlayer() {
        assertEquals(ID, Alias.rename(account(), "Someone").getId());
    }

    @Test
    void renameChangesTheName() {
        assertEquals("Someone", Alias.rename(account(), "Someone").getName());
    }

    @Test
    void renameDropsTheAccountSkin() {
        assertTrue(Alias.rename(account(), "Someone").getProperties().get(Alias.SKIN_PROPERTY).isEmpty());
    }

    @Test
    void renameKeepsEveryOtherProperty() {
        GameProfile account = account();
        account.getProperties().put("other", new Property("other", "kept"));
        assertEquals("kept", Alias.rename(account, "Someone").getProperties().get("other").iterator().next().value());
    }

    @Test
    void renameLeavesTheAccountProfileAlone() {
        GameProfile account = account();
        GameProfile renamed = Alias.rename(account, "Someone");
        assertNotSame(account, renamed);
        assertEquals("AccountName", account.getName());
        assertEquals(1, account.getProperties().size());
    }

    @Test
    void usableAcceptsAnyScript() {
        assertTrue(Alias.usable("Someone"));
        assertTrue(Alias.usable("a"));
        assertTrue(Alias.usable("sixteen_chars_16"));
        assertTrue(Alias.usable("名前"));
        assertTrue(Alias.usable("かぼちゃ"));
    }

    @Test
    void usableStillAcceptsEverySingleCharacterTheGameWould() {
        for (char character = '!'; character <= '~'; character++) {
            assertTrue(Alias.usable(String.valueOf(character)), String.valueOf(character));
        }
    }

    @Test
    void usableCountsCharactersNotBytes() {
        assertTrue(Alias.usable("あ".repeat(Alias.LENGTH)));
        assertFalse(Alias.usable("あ".repeat(Alias.LENGTH + 1)));
    }

    @Test
    void usableRejectsEmptyAndTooLong() {
        assertFalse(Alias.usable(""));
        assertFalse(Alias.usable("seventeen_chars17"));
    }

    @Test
    void theOnlyNameTheGameAcceptsAndCognomenDoesNotIsTheEmptyOne() {
        //? if >=1.20.2 {
        assertTrue(StringUtil.isValidPlayerName(""));
        //?} else
        /*assertTrue(ServerLoginPacketListenerImpl.isValidUsername(""));*/
        assertFalse(Alias.usable(""));
    }

    @Test
    void usableRejectsWhitespaceOfEveryWidth() {
        assertFalse(Alias.usable("two words"));
        assertFalse(Alias.usable("tab\there"));
        assertFalse(Alias.usable("全　角"));
    }

    @Test
    void usableRejectsFormattingAndControlCharacters() {
        assertFalse(Alias.usable(ChatFormatting.PREFIX_CODE + "cRed"));
        assertFalse(Alias.usable("two\nlines"));
    }

    @Test
    void anEmptyNameFallsBackToThePlaceholderNotTheAccount() {
        assertEquals(Alias.PLACEHOLDER, Alias.resolve(""));
    }

    @Test
    void aUsableNameIsUsedAsItIs() {
        assertEquals("Someone", Alias.resolve("Someone"));
    }

    @Test
    void anUnusableNameFallsBackToThePlaceholderNotTheAccount() {
        assertEquals(Alias.PLACEHOLDER, Alias.resolve("two words"));
        assertEquals(Alias.PLACEHOLDER, Alias.resolve("seventeen_chars17"));
    }

    @Test
    void aJapaneseNameIsUsedAsItIs() {
        assertEquals("名前", Alias.resolve("名前"));
    }

    @Test
    void thePlaceholderIsUsableItself() {
        assertTrue(Alias.usable(Alias.PLACEHOLDER));
    }

    @Test
    void onlyA64By64ImageFitsAsASkin() {
        assertTrue(Look.fits(64, 64));
        assertFalse(Look.fits(64, 32));
        assertFalse(Look.fits(128, 128));
    }
}
