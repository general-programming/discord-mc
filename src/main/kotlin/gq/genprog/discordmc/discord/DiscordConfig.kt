package gq.genprog.discordmc.discord

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
data class DiscordConfig(val token: String, val channelId: Long, val webhook: Webhook?, val messages: Messages = Messages()) {
    data class Webhook(val url: String, val enabled: Boolean, val usernamePrefix: String = "[MC] ")
    data class Messages(val serverStarted: String = "Server started!", val serverStopped: String = "Server stopped!")
}