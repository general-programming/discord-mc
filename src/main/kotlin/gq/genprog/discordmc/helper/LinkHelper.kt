package gq.genprog.discordmc.helper

import gq.genprog.simpletweaker.config.JsonConfigLoader
import java.io.File
import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class LinkHelper {
    companion object {
        val loader = JsonConfigLoader()

        fun fromFile(saveFile: File): LinkHelper {
            if (!saveFile.exists()) {
                return LinkHelper().apply {
                    loader.writeConfig(saveFile, this)
                }
            }

            return loader.loadConfig(saveFile, LinkHelper::class.java).also { it.saveFile = saveFile }
        }
    }

    @Transient var saveFile: File? = null
    @Transient val linkCodes: HashMap<UUID, Long> = hashMapOf()
    val links: HashMap<UUID, Long> = hashMapOf()

    fun setLink(mc: UUID, discord: Long) {
        links[mc] = discord
    }

    fun getLink(mc: UUID): Long? {
        return links[mc]
    }

    fun stashPotentialLink(mc: UUID, discordId: Long) {
        linkCodes[mc] = discordId
    }

    @Synchronized fun saveToFile() {
        saveFile?.also {
            loader.writeConfig(it, this)
        }
    }
}