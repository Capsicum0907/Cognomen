# Cognomen

[日本語](README.ja.md)

Play singleplayer under a name of your choosing instead of your account's. Made for recording and streaming: the name your Microsoft account carries never has to appear on screen, and you do not need a second account to keep it off.

Client only. Minecraft 1.21.1, NeoForge 21.1.

## Use

Open **Mods → Cognomen → Config** and type a name, or edit `config/cognomen-client.toml`:

```toml
name = "Someone"
```

The name takes effect from the next world you open. It starts as `player`. With Cognomen installed, singleplayer never uses your account name; to play under it, remove the mod.

A name follows the game's own rule for player names: 1 to 16 characters, each one a printable ASCII character other than a space (letters, digits and `` !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ ``). An empty name, or one that breaks the rule, is not used; you play as `player` instead, and a notice says so.

## How it works

In singleplayer the game runs its own server inside the client, and hands it your account's profile when the world opens. Cognomen hands over a copy with the name replaced. Everything the server says about you afterwards is built from that copy, so it carries the new name without being patched one place at a time: chat, join and leave messages, death messages, advancements, command feedback, player heads, the author of a signed book, and the name shown to others when the world is opened to LAN.

What stays the same:

- **Your UUID.** Your inventory, position, statistics and advancements are saved under it, so they are all still yours.
- **Your skin.** The copy carries your account's skin with it.

Two places are covered separately. The end poem reads the name straight from the account rather than from the server. And the game remembers the last name each UUID joined under, so the first time you join under a new name it would announce you as "*new name* (formerly known as *old name*)"; for the owner of a singleplayer world, that earlier name is never looked up.

## Worth knowing

- **Singleplayer only.** On a server, the name comes from the server.
- **Text already in a world keeps the name it was written with.** Signs you typed it on, books signed before, player heads, and scoreboard entries (which are kept by name) still show the account name.
- **Log files still contain the account name.** The launcher passes it to the game as `--username`, and the first lines of `logs/latest.log` and `logs/debug.log` record the launch arguments before any mod is loaded, so no mod can keep it out. The game then records it once more as `Setting user:`. Do not share these files if that matters.

## License

MIT
