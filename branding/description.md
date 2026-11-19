Cognomen lets you play under a name and a skin of your choosing instead of your account's. It is made for recording and streaming: the name your Microsoft account carries never has to appear on screen, and you do not need a second account to keep it off.

## Your name

Set a name in the config screen (Mods → Cognomen → Config on Forge and NeoForge, Mod Menu on Fabric), or in `config/cognomen-client.toml`:

```toml
name = "Someone"
```

The game then calls you by it everywhere it says your name: chat, join and leave messages, death messages, advancements, command feedback, player heads, the author of a signed book, the end poem, and the name others see when the world is opened to LAN. It starts as `Steve`.

A name is 1 to 16 characters, with no spaces and no formatting codes. Any script is allowed, Japanese included, and the limit counts characters rather than bytes. Letters, digits and `_` are the safe choice if you type commands.

## Your look

```toml
skin = "config/cognomen/skin.png"
slimArms = false
```

Your account's skin and cape are left out. You look like Steve, or like a 64×64 PNG of your own, given as a path from the game folder, on the wide or the slim-armed model.

## Never back to the account

An empty name, a name the game would not accept, or a skin file that is missing, unreadable or the wrong size falls back to Steve. It never falls back to your account, and a notice tells you what happened.

## Multiplayer is off

A server has to be told your account name to let you in, so while Cognomen is installed there is no multiplayer and no Realms. Their buttons are gone from the title screen, and anything else that tries to connect gets a notice instead. To play online, remove the mod.

## Worth knowing

- **The first lines of the log still contain your account name.** The launcher passes it to the game as `--username`, and it is written down before any mod is loaded. Do not share `logs/latest.log` or `logs/debug.log` if that matters.
- **Text already in a world keeps the name it was written with**: signs, books signed before, player heads made before, and scoreboard entries.
- Changes take effect from the next world you open.

## Under it

The game keeps your account in one object for the whole session, and everything that wants your name or your look asks it, or asks for the profile made from it. Cognomen answers those two questions and nothing else. The game itself, and any other mod that reads your name from the game, then get the chosen name without each place being patched in turn.

Your UUID is untouched. Inventories, positions, statistics and advancements are saved under it, so they are all still yours. The one thing the game remembers by name is the last name each UUID joined under, which would otherwise announce you as "*new name* (formerly known as *old name*)"; for the owner of a singleplayer world, that earlier name is never looked up.

## Requirements

Minecraft 1.21.1 on NeoForge or Fabric; 1.20.1, 1.19.2, 1.18.2 and 1.16.5 on Forge or Fabric. Client only. Nothing else is needed, Fabric API included; on Fabric the config screen is reached through Mod Menu when it is installed. MIT licensed.

---

# 日本語

Cognomen は、アカウントの名前とスキンではなく、自分で決めた名前とスキンで遊ぶための Mod です。実況・配信の録画向けで、Microsoft アカウントに紐づいた名前を画面に出さずに済みます。そのために2つ目のアカウントを買う必要もありません。

## 名前

設定画面（Forge と NeoForge では Mods → Cognomen → 設定、Fabric では Mod Menu から）か、`config/cognomen-client.toml` で名前を決めます。

```toml
name = "Someone"
```

ゲームはあなたの名前を出すすべての場所で、この名前を使います。チャット、参加・退出メッセージ、死亡メッセージ、進捗、コマンドの応答、プレイヤーの頭、署名した本の著者、エンドポエム、LAN に公開したときに他の人に見える名前。初期値は `Steve` です。

名前は 1〜16 文字で、空白と装飾コードは使えません。日本語も使えます。上限はバイト数ではなく文字数です。コマンドを打つなら、英数字と `_` だけにしておくのが無難です。

## 見た目

```toml
skin = "config/cognomen/skin.png"
slimArms = false
```

アカウントのスキンとマントは外されます。見た目は Steve か、自分で用意した 64×64 の PNG（ゲームフォルダからの場所で指定）になり、腕の太いモデルと細いモデルを選べます。

## アカウントには戻らない

名前が空欄のとき、ゲームが受け付けない名前のとき、スキンのファイルが無い・読めない・大きさが違うときは、Steve に戻ります。アカウントに戻ることはなく、何が起きたかを通知で知らせます。

## マルチプレイは使えません

サーバーに入るにはアカウント名を伝える必要があるため、Cognomen を入れている間はマルチプレイも Realms も使えません。タイトル画面のボタンは消してあり、それ以外の経路で接続しようとしたときも、接続せずに案内を出します。オンラインで遊ぶときは Mod を外してください。

## 知っておくこと

- **ログの冒頭には、アカウント名が残ります。** ランチャーが `--username` としてゲームに渡し、Mod が読み込まれる前に書き出されるためです。気になる場合は `logs/latest.log` と `logs/debug.log` を共有しないでください。
- **ワールドに既に書かれた文字は、書いたときの名前のままです。** 看板、以前に署名した本、以前に作ったプレイヤーの頭、スコアボードの項目。
- 変更は次に開くワールドから使われます。

## 中身

ゲームはアカウントを起動から終了まで1つの入れ物に持っていて、名前や見た目を知りたいものは、この入れ物か、そこから作るプロフィールに問い合わせます。Cognomen が答えるのはこの2つの問い合わせだけです。それだけで、ゲーム本体も、ゲームから名前を読むほかの Mod も、表示を一箇所ずつ直さなくても設定した名前を受け取ります。

UUID は変えません。持ち物・位置・統計・進捗はこの番号で保存されているので、すべてそのまま引き継がれます。ゲームが名前で覚えているのは、UUID ごとの「前回参加したときの名前」だけで、放っておくと「新しい名前 (formerly known as 前の名前)」と参加を告げてしまいます。シングルプレイのワールドの持ち主については、この前の名前を引かないようにしています。

## 動作環境

Minecraft 1.21.1 の NeoForge と Fabric、1.20.1・1.19.2・1.18.2・1.16.5 の Forge と Fabric。クライアント専用。ほかに必要な Mod はありません（Fabric API も不要）。Fabric では、Mod Menu を入れていればそこから設定画面を開けます。MIT ライセンス。
