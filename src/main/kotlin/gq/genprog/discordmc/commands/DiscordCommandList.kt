package gq.genprog.discordmc.commands

import gq.genprog.discordmc.discord.DiscordClient
import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.entities.Message

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordCommandList: ICommand {
    override fun getAliases() = arrayOf("list", "online")

    override fun execute(client: DiscordClient, message: Message, args: List<String>) {
        val online = Helper.getMinecraftServer().getOnlinePlayers()

        if (online.isEmpty()) {
            message.channel.sendMessage("No players online.").queue()
            return
        }

        val onlineText = online.joinToString { it.name }
        message.channel.sendMessage("Currently online: $onlineText").queue()
    }
}