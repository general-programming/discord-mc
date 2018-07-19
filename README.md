# DiscordMC

A basic Discord <-> Minecraft link for 1.13 servers.

This is a tweak for [SimpleTweaker](https://github.com/general-programming/simple-tweaker).

### building

you'll need SimpleTweaker in your local maven repository

```sh
git clone https://github.com/general-programming/discord-mc.git
cd discord-mc
gradle shadowJar
cp build/libs/discord-mc-1.0.0-all.jar /my/mc/server/tweaks
```