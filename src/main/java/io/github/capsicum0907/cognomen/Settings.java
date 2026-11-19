package io.github.capsicum0907.cognomen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Settings {
    public static final String FILE = Cognomen.MODID + "-client.toml";

    static final String NAME = "name";
    static final String SKIN = "skin";
    static final String SLIM_ARMS = "slimArms";

    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile Values current;

    public static final class Values {
        public static final Values DEFAULT = new Values(Alias.PLACEHOLDER, "", false);

        public final String name;
        public final String skin;
        public final boolean slimArms;

        public Values(String name, String skin, boolean slimArms) {
            this.name = name;
            this.skin = skin;
            this.slimArms = slimArms;
        }
    }

    private Settings() {
    }

    public static boolean loaded() {
        return current != null;
    }

    public static Values get() {
        Values values = current;
        return values == null ? Values.DEFAULT : values;
    }

    public static void load() {
        Path file = Platform.configDir().resolve(FILE);
        try {
            current = parse(new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
        } catch (NoSuchFileException missing) {
            save(Values.DEFAULT);
        } catch (IOException | IllegalArgumentException unreadable) {
            LOGGER.error("Could not read {}, so the defaults are used until it is saved again", file, unreadable);
            current = Values.DEFAULT;
        }
    }

    public static void save(Values values) {
        current = values;
        Path file = Platform.configDir().resolve(FILE);
        try {
            Files.createDirectories(file.getParent());
            Files.write(file, format(values).getBytes(StandardCharsets.UTF_8));
        } catch (IOException unwritable) {
            LOGGER.error("Could not write {}", file, unwritable);
        }
    }

    static Values parse(String text) {
        Map<String, String> strings = new LinkedHashMap<>();
        Map<String, Boolean> booleans = new LinkedHashMap<>();
        for (String raw : text.split("\r?\n", -1)) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            int equals = line.indexOf('=');
            if (equals < 0) {
                throw new IllegalArgumentException("No '=' in: " + raw);
            }
            String key = line.substring(0, equals).trim();
            String value = line.substring(equals + 1).trim();
            if (value.startsWith("\"")) {
                strings.put(key, basicString(value));
            } else if (value.startsWith("'")) {
                strings.put(key, literalString(value));
            } else if (value.startsWith("true") || value.startsWith("false")) {
                booleans.put(key, value.startsWith("true"));
            } else {
                throw new IllegalArgumentException("Unsupported value in: " + raw);
            }
        }
        return new Values(
                strings.getOrDefault(NAME, Values.DEFAULT.name),
                strings.getOrDefault(SKIN, Values.DEFAULT.skin),
                booleans.getOrDefault(SLIM_ARMS, Values.DEFAULT.slimArms));
    }

    static String format(Values values) {
        List<String> lines = java.util.Arrays.asList(
                NAME + " = " + quote(values.name),
                SKIN + " = " + quote(values.skin),
                SLIM_ARMS + " = " + values.slimArms);
        return String.join("\n", lines) + "\n";
    }

    private static String basicString(String value) {
        StringBuilder out = new StringBuilder();
        for (int i = 1; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '"') {
                return out.toString();
            }
            if (c != '\\') {
                out.append(c);
                continue;
            }
            char escape = value.charAt(++i);
            switch (escape) {
                case 'b': out.append('\b'); break;
                case 't': out.append('\t'); break;
                case 'n': out.append('\n'); break;
                case 'f': out.append('\f'); break;
                case 'r': out.append('\r'); break;
                case '"': out.append('"'); break;
                case '\\': out.append('\\'); break;
                case 'u':
                    out.append((char) Integer.parseInt(value.substring(i + 1, i + 5), 16));
                    i += 4;
                    break;
                case 'U':
                    out.appendCodePoint(Integer.parseInt(value.substring(i + 1, i + 9), 16));
                    i += 8;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown escape \\" + escape + " in: " + value);
            }
        }
        throw new IllegalArgumentException("Unterminated string: " + value);
    }

    private static String literalString(String value) {
        int end = value.indexOf('\'', 1);
        if (end < 0) {
            throw new IllegalArgumentException("Unterminated string: " + value);
        }
        return value.substring(1, end);
    }

    private static String quote(String text) {
        StringBuilder out = new StringBuilder("\"");
        text.codePoints().forEach(c -> {
            if (c == '"' || c == '\\') {
                out.append('\\').appendCodePoint(c);
            } else if (c < 0x20 || c == 0x7f) {
                out.append(String.format("\\u%04X", c));
            } else {
                out.appendCodePoint(c);
            }
        });
        return out.append('"').toString();
    }
}
