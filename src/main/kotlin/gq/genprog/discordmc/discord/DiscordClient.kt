package gq.genprog.discordmc.discord

import gq.genprog.discordmc.commands.ICommand
import gq.genprog.discordmc.helper.LinkHelper
import gq.genprog.simpletweaker.api.IPlayer
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.webhook.WebhookClient
import net.dv8tion.jda.webhook.WebhookClientBuilder
import net.dv8tion.jda.webhook.WebhookMessageBuilder
import java.io.File

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordClient(val config: DiscordConfig, configDir: File) {
    val jda: JDA = JDABuilder(AccountType.BOT).apply {
        setToken(config.token)
        setAutoReconnect(true)
        addEventListener(DiscordEventListener(this@DiscordClient))
    }.buildAsync()

    val commands: HashMap<String, ICommand> = hashMapOf()
    val links = LinkHelper.fromFile(configDir.resolve("discord_links.json"))

    val isWebhookEnabled get() = config.webhook?.enabled ?: false
    val webhook: WebhookClient? =
            if (isWebhookEnabled && config.webhook != null)
                WebhookClientBuilder(config.webhook.url).build()
            else null

    fun sendWebhookMessage(author: IPlayer, text: String) {
        val link = links.getLink(author.uid)
        val message = if (link != null) {
            val discordUser = jda.getUserById(link)

            WebhookMessageBuilder().apply {
                setUsername(discordUser.name)
                setAvatarUrl(discordUser.effectiveAvatarUrl)
                setContent(text)
            }.build()
        } else {
            WebhookMessageBuilder().apply {
                setUsername(config.webhook!!.usernamePrefix + author.name)
                setAvatarUrl("https://visage.surgeplay.com/head/256/${author.uid}.png")
                setContent(text)
            }.build()
        }

        webhook?.send(message)
    }

    fun sendNormalMessage(author: IPlayer, text: String) {
        jda.getTextChannelById(config.channelId)?.apply {
            sendMessage("**[${author.name}]** $text").queue()
        }
    }

    fun sendMessage(author: IPlayer, text: String) {
        if (isWebhookEnabled) {
            this.sendWebhookMessage(author, text)
        } else {
            this.sendNormalMessage(author, text)
        }
    }

    fun sendSystemMessage(text: String) {
        jda.getTextChannelById(config.channelId)?.apply {
            sendMessage(text).queue()
        }
    }

    fun sendSystemBlocking(text: String) {
        jda.getTextChannelById(config.channelId)?.apply {
            sendMessage(text).complete()
        }
    }

    fun addCommand(command: ICommand) {
        for (alias in command.getAliases()) {
            commands[alias] = command
        }
    }
}