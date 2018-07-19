package gq.genprog.discordmc.commands

import gq.genprog.discordmc.discord.DiscordClient
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
            val code = client.links.generateLinkCode(this.uid)

            this.sendMessage("Your Discord link code is \u00A76$code\u00A7r.\n" +
                    "Use 'mc%accept $code' in Discord DMs to confirm the link.")
            message.channel.sendMessage("${message.author.asMention} Sent you a link code in Minecraft.").queue()
            return
        }

        message.channel.sendMessage("Couldn't find that player! Are you logged in?").queue()
    }
}