package gq.genprog.discordmc.discord

import net.dv8tion.jda.core.entities.User

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
fun User.fullName(): String {
    return "$name#$discriminator"
}