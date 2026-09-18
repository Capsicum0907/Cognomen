# Cognomen

[日本語](README.ja.md)

Play under a name of your choosing instead of your account's. Made for recording and streaming: the name your Microsoft account carries never has to appear on screen, and you do not need a second account to keep it off.

Client only. Minecraft 1.21.1, NeoForge 21.1.

## Use

Open **Mods → Cognomen → Config**, or edit `config/cognomen-client.toml`:

```toml
name = "Someone"
skin = "config/cognomen/skin.png"
slimArms = false
```

Both take effect from the next world you open. The name starts as `Steve`, and the skin starts empty, which means you look like Steve.

A name follows the game's own rule for player names: 1 to 16 characters, each one a printable ASCII character other than a space (letters, digits and `` !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ ``). An empty name, or one that breaks the rule, is not used; you play as `Steve` instead, and a notice says so. Letters, digits and `_` are the safe choice: they are what real account names use, and commands take them without quotes.

`skin` is a 64×64 PNG, given as a path from the game folder. `slimArms` puts it on the slim-armed model. If the file is missing, cannot be read as a PNG, or is another size, you look like Steve, and a notice says why.

With Cognomen installed, your account name and your account's skin are never used. To play as yourself, remove the mod.

## How it works

The game keeps your account in one object for the whole session, and everything that wants your name asks it or asks for your profile. Cognomen answers both with the chosen name, and leaves your account's skin and cape out of the profile. The rest follows without being patched one place at a time: the world you play in, chat, join and leave messages, death messages, advancements, command feedback, player heads, the author of a signed book, the end poem, the name shown to others when the world is opened to LAN, and any other mod that reads your name from the game.

What stays and what changes:

- **Your UUID stays.** Your inventory, position, statistics and advancements are saved under it, so they are all still yours.
- **What you look like is Steve, or the skin you set.** Without a skin in the profile the game would pick a default one from your UUID; Cognomen makes that default Steve, or your own PNG when one is set. That one place is where your look comes from, so it reaches your player, your player head and anything else that draws you.

The game also remembers the last name each UUID joined under, and the first time you join under a new name it would announce you as "*new name* (formerly known as *old name*)". For the owner of a singleplayer world, that earlier name is never looked up.

## Multiplayer is off

A server has to be told your account name to let you in, so while Cognomen is installed there is no multiplayer and no Realms. The Multiplayer and Realms buttons are gone from the title screen, along with the Realms notifications beside them, and anything else that tries to connect gets a notice instead.

## Worth knowing

- **The first lines of the log still contain your account name.** The launcher passes it to the game as `--username`, and `logs/latest.log` and `logs/debug.log` begin by recording the launch arguments before any mod is loaded, so no mod can keep it out. Do not share these files if that matters.
- **Text already in a world keeps the name it was written with.** Signs you typed it on, books signed before, player heads made before, and scoreboard entries (which are kept by name) still show the account name.

## License

MIT
