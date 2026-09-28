Names in any script.

- The name can now be written in Japanese, or in any other script. The game's own rule for player names allows printable ASCII only, and Cognomen lifts it where the name is checked.
- The rest of the rule stands: 1 to 16 characters, no spaces, no formatting codes. The limit counts characters rather than bytes, so 16 Japanese characters fit.
- A command that takes a player name reads letters, digits, `_`, `-`, `.` and `+` as they are; any other name has to be quoted, as in `/tp "名前" ~ ~ ~`. `@s` and `@p` work whatever the name is.

Nothing else has changed.

For NeoForge 21.1 on Minecraft 1.21.1.

---

どの文字でも名前にできます。

- 名前を日本語で書けるようになりました。ほかの文字でも同じです。ゲーム本体のプレイヤー名の規則は半角英数記号だけですが、Cognomen が名前を検査する箇所でこの規則を外しています。
- 残りの規則はそのままです。1〜16 文字で、空白と装飾コードは使えません。上限はバイト数ではなく文字数なので、日本語でも 16 文字入ります。
- プレイヤー名を受け取るコマンドがそのまま読めるのは英数字と `_` `-` `.` `+` で、それ以外の名前は `/tp "名前" ~ ~ ~` のように引用符で囲む必要があります。`@s` と `@p` は名前に関係なく使えます。

その他の挙動は変わりません。

Minecraft 1.21.1 の NeoForge 21.1 向け。
