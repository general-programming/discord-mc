package gq.genprog.discordmc.discord

import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordEventListener(val config: DiscordConfig): ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("Discord connected")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot or event.isWebhookMessage or (event.channel.idLong != config.channelId))
            return

        val content = stripDangerMentions(event.message.contentStripped)
        val text = "[\u00A79D\u00A7r]<${event.author.name}> $content"
        Helper.getMinecraftServer().broadcast(text)
    }

    fun stripDangerMentions(text: String): String {
        return text
                .replace("@everyone", "@\u200beveryone")
                .replace("@here", "@\u200bhere")
    }
}