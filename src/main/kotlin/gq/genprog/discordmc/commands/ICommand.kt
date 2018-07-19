package gq.genprog.discordmc.commands

import gq.genprog.discordmc.discord.DiscordClient
import net.dv8tion.jda.core.entities.Message

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ICommand {
    fun getAliases(): Array<String>
    fun execute(client: DiscordClient, message: Message, args: List<String>)
}