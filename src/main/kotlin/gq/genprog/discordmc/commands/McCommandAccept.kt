package gq.genprog.discordmc.commands

import gq.genprog.discordmc.discord.DiscordClient
import gq.genprog.discordmc.discord.fullName
import gq.genprog.simpletweaker.api.ICommand
import gq.genprog.simpletweaker.api.ICommandSender
import gq.genprog.simpletweaker.api.IPlayer

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class McCommandAccept(val client: DiscordClient) : ICommand {
    override fun getAliases() = arrayOf("discord-accept")
    override fun getDescription() = "Accept a Discord link request."

    override fun execute(sender: ICommandSender, array: Array<String>) {
        if (sender !is IPlayer)
            return

        client.links.linkCodes.remove(sender.uid)?.also { discordId ->
            val discordUser = client.jda.getUserById(discordId)

            client.links.setLink(sender.uid, discordId)
            client.links.saveToFile()

            sender.sendMessage("Linked account ${discordUser.fullName()} successfully.")
            return
        }

        sender.sendMessage("\u00A7cYou don't have an active link request!\u00A7r")
    }
}