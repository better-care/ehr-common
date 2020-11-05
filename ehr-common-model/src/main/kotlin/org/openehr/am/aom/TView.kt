package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TView : AmObject() {
    var constraints: MutableList<Constraints> = mutableListOf()

    @Serializable
    class Constraints : AmObject(){
        var items: MutableList<Items> = mutableListOf()
        lateinit var path: String

        @Serializable
        class Items : AmObject(){
            @Contextual
            lateinit var value: Any
            lateinit var id: String
        }
    }
}