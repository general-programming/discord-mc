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

        val text = "[\u00A79D\u00A7r]<${event.author.name}> ${event.message.contentStripped}"
        Helper.getMinecraftServer().broadcast(text)
    }
}