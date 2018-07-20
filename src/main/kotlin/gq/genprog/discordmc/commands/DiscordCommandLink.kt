package gq.genprog.discordmc.commands

import gq.genprog.discordmc.discord.DiscordClient
import gq.genprog.discordmc.discord.fullName
import gq.genprog.simpletweaker.Helper
import net.dv8tion.jda.core.entities.Message

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class DiscordCommandLink: ICommand {
    override fun getAliases() = arrayOf(
            "link"
    )

    override fun execute(client: DiscordClient, message: Message, args: List<String>) {
        if (args.isEmpty()) {
            message.channel.sendMessage("Usage: `mc%link <Minecraft username>`").queue()
            return
        }

        val username = args[0]

        Helper.getMinecraftServer().getPlayer(username)?.apply {
            client.links.stashPotentialLink(this.uid, message.author.idLong)

            this.sendMessage("\u00A76${message.author.fullName()}\u00A7r" +
                    " has requested to link their Discord account to you.\n" +
                    "Use the command \u00A76/discord-accept\u00A7r to accept.")
            message.channel.sendMessage("${message.author.asMention} Sent you instructions in Minecraft.").queue()
            return
        }

        message.channel.sendMessage("Couldn't find that player! Are you logged in?").queue()
    }
}