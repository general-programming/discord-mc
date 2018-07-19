package gq.genprog.discordmc.discord

import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.events.ExceptionEvent
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent
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

        if (handleCommand(event.message))
            return

        val content = event.message.contentStripped
        var text = "[\u00A79D\u00A7r]<${event.author.name}> $content"
        if (!event.message.attachments.isEmpty()) {
            val n = event.message.attachments.size
            val attachText = if (n == 1) "Attachment" else "$n Attachments"

            text += " \u00A76[$attachText]\u00A7r"
        }

        Helper.getMinecraftServer().broadcast(text)
    }

    override fun onPrivateMessageReceived(event: PrivateMessageReceivedEvent) {
        this.handleCommand(event.message)
    }

    fun handleCommand(message: Message): Boolean {
        if (message.contentRaw.startsWith("mc%")) {
            val parts = message.contentRaw.split(' ')
            val (_, cmdName) = parts[0].split('%')
            val rest = parts.subList(1, parts.size)

            try {
                client.commands[cmdName]?.execute(client, message, rest)
            } catch (err: Throwable) {
                err.printStackTrace()
            }

            return true
        }

        return false
    }

    override fun onException(event: ExceptionEvent) {
        event.cause.printStackTrace()
    }
}