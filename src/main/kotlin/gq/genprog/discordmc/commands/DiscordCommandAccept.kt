package gq.genprog.discordmc.commands

import gq.genprog.discordmc.discord.DiscordClient
import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.entities.Message

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordCommandAccept: ICommand {
    override fun getAliases() = arrayOf(
            "accept"
    )

    override fun execute(client: DiscordClient, message: Message, args: List<String>) {
        if (args.isEmpty()) {
            message.channel.sendMessage("Usage: `mc%accept <code>`").queue()
            return
        }

        val code = args[0].toInt()
        client.links.linkCodes.remove(code)?.apply {
            client.links.setLink(this, message.author.idLong)
            client.links.saveToFile()

            val mcUsername = Helper.getMinecraftServer().getPlayer(this)!!.getUsername()
            message.channel.sendMessage("Linked $mcUsername to ${message.author.name}#${message.author.discriminator}").queue()
            return
        }

        message.channel.sendMessage("That link code isn't active!").queue()
    }
}