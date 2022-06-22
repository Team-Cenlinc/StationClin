package org.fetarute.stationclin

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info

object StationClin : Plugin() {

    override fun onEnable() {
        info("Successfully running ExamplePlugin!")
    }
}