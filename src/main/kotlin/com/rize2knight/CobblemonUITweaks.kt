package com.rize2knight

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object CobblemonUITweaks : ModInitializer {
    private val logger = LoggerFactory.getLogger("cobblemonuitweaks")

	override fun onInitialize() {

		logger.info("CobblemonUITweaks running/initializing")
	}
}