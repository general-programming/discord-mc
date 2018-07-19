package gq.genprog.discordmc.discord

import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.events.ExceptionEvent
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordEventListener(val client: DiscordClient): ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("Discord connected")

        client.sendSystemMessage(client.config.messages.serverStarted)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot or event.isWebhookMessage or (event.channel.idLong != client.config.channelId))
            return

        if (event.message.contentRaw.startsWith("mc%")) {
            val parts = event.message.contentRaw.split(' ')
            val (_, cmdName) = parts[0].split('%')
            val rest = parts.subList(1, parts.size)

            try {
                client.commands[cmdName]?.execute(client, event.message, rest)
            } catch (err: Throwable) {
                err.printStackTrace()
            }
            return
        }

        val content = stripDangerMentions(event.message.contentStripped)
        val text = "[\u00A79D\u00A7r]<${event.author.name}> $content"
        Helper.getMinecraftServer().broadcast(text)
    }

    override fun onException(event: ExceptionEvent) {
        event.cause.printStackTrace()
    }

    fun stripDangerMentions(text: String): String {
        return text
                .replace("@everyone", "@\u200beveryone")
                .replace("@here", "@\u200bhere")
    }
}