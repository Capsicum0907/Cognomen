package io.github.capsicum0907.cognomen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SettingsTest {
    private static void assertSame(Settings.Values expected, Settings.Values actual) {
        assertEquals(expected.name, actual.name);
        assertEquals(expected.skin, actual.skin);
        assertEquals(expected.slimArms, actual.slimArms);
    }

    @Test
    void readsTheFileTheNeoForgeConfigWrote() {
        Settings.Values values = Settings.parse("name = \"どなたか\"\nskin = \"steve-red.png\"\nslimArms = false\n");
        assertSame(new Settings.Values("どなたか", "steve-red.png", false), values);
    }

    @Test
    void writesWhatItReads() {
        Settings.Values values = new Settings.Values("名前_1", "config/cognomen/skin.png", true);
        assertSame(values, Settings.parse(Settings.format(values)));
    }

    @Test
    void quotesAndBackslashesSurviveTheRoundTrip() {
        Settings.Values values = new Settings.Values("a\"b\\c", "C:\\skins\\me.png", false);
        assertSame(values, Settings.parse(Settings.format(values)));
    }

    @Test
    void controlCharactersSurviveTheRoundTrip() {
        Settings.Values values = new Settings.Values("tab\there", "line\nbreak", false);
        assertSame(values, Settings.parse(Settings.format(values)));
    }

    @Test
    void theWrittenFileIsOneKeyPerLine() {
        assertEquals("name = \"Steve\"\nskin = \"\"\nslimArms = false\n", Settings.format(Settings.Values.DEFAULT));
    }

    @Test
    void missingKeysFallBackToTheDefaults() {
        assertSame(new Settings.Values("Someone", "", false), Settings.parse("name = \"Someone\"\n"));
    }

    @Test
    void commentsBlankLinesAndWindowsLineEndingsAreSkipped() {
        Settings.Values values = Settings.parse("# a comment\r\n\r\nname = 'Literal'\r\nslimArms = true\r\n");
        assertSame(new Settings.Values("Literal", "", true), values);
    }

    @Test
    void unicodeEscapesAreDecoded() {
        assertEquals("名", Settings.parse("name = \"\\u540D\"\n").name);
        assertEquals("😀", Settings.parse("name = \"\\U0001F600\"\n").name);
    }

    @Test
    void aLineWithoutAValueIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Settings.parse("name\n"));
    }

    @Test
    void anUnterminatedStringIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Settings.parse("name = \"open\n"));
    }
}
