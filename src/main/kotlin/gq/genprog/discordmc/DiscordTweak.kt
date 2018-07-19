package gq.genprog.discordmc

import gq.genprog.discordmc.discord.DiscordClient
import gq.genprog.discordmc.discord.DiscordConfig
import gq.genprog.simpletweaker.config.JsonConfigLoader
import gq.genprog.simpletweaker.events.EventHandler
import gq.genprog.simpletweaker.events.PlayerChatEvent
import gq.genprog.simpletweaker.events.TweakRunEvent
import gq.genprog.simpletweaker.tweaks.ITweak
import gq.genprog.simpletweaker.tweaks.TweakStage

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordTweak: ITweak {
    var client: DiscordClient? = null

    override fun getTweakStage() = TweakStage.POST_WORLD

    override fun runTweak(ev: TweakRunEvent) {
        val configFile = ev.configDir.resolve("discord.json")
        val loader = JsonConfigLoader()

        if (!configFile.exists()) {
            val config = DiscordConfig("", 0L, DiscordConfig.Webhook("", false))

            loader.writeConfig(configFile, config)
        }

        val config = loader.loadConfig(configFile, DiscordConfig::class.java)
        if (config.token.isEmpty()) {
            println("No token provided; disabling discord tweak")
            return
        }

        this.client = DiscordClient(config)
    }

    @EventHandler fun onPlayerChat(event: PlayerChatEvent) {
        this.client?.sendMessage(event.player, event.message)
    }
}