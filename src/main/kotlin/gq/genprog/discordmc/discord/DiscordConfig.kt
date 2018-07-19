package gq.genprog.discordmc.discord

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
data class DiscordConfig(val token: String, val channelId: Long, val webhook: Webhook?) {
    data class Webhook(val url: String, val enabled: Boolean, val usernamePrefix: String = "[MC] ")
}