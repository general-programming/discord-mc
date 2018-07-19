package gq.genprog.discordmc.discord

import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordEventListener: ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("Discord connected")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot or event.isWebhookMessage)
            return

        val text = "[\u00A79D\u00A7r]<${event.author.name}> ${event.message.contentStripped}"
        Helper.getMinecraftServer().broadcast(text)
    }
}