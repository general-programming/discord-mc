package gq.genprog.discordmc

import gq.genprog.discordmc.commands.DiscordCommandLink
import gq.genprog.discordmc.commands.DiscordCommandList
import gq.genprog.discordmc.commands.McCommandAccept
import gq.genprog.discordmc.discord.DiscordClient
import gq.genprog.discordmc.discord.DiscordConfig
import gq.genprog.simpletweaker.config.JsonConfigLoader
import gq.genprog.simpletweaker.events.*
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

        this.client = DiscordClient(config, ev.configDir)

        this.client?.apply {
            addCommand(DiscordCommandLink())
            addCommand(DiscordCommandList())

            ev.registerCommand(McCommandAccept(this))
        }
    }

    @EventHandler fun onPlayerChat(event: PlayerChatEvent) {
        val content = stripDangerMentions(event.message)

        this.client?.sendMessage(event.player, content)
    }

    @EventHandler fun onPlayerJoin(event: PlayerJoinEvent) {
        this.client?.sendMessage(event.player, "*joined the server*")
    }

    @EventHandler fun onPlayerLeave(event: PlayerLeaveEvent) {
        this.client?.sendMessage(event.player, "*left the server*")
    }

    @EventHandler fun onServerStopping(event: ServerStoppingEvent) {
//        this.client?.apply {
//            sendSystemBlocking(config.messages.serverStopped)
//        }
    }

    fun stripDangerMentions(text: String): String {
        return text
                .replace("@everyone", "@\u200beveryone")
                .replace("@here", "@\u200bhere")
    }
}