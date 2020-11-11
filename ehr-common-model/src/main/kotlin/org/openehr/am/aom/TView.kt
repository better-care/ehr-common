package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotEmpty
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TView : AmObject(), Serializable {
    var constraints: MutableList<Constraints> = mutableListOf()

    class Constraints : AmObject(), Serializable {
        @RequiresNotEmpty
        var items: MutableList<Items> = mutableListOf()
        lateinit var path: String

        class Items : AmObject(), Serializable {
            lateinit var value: Any
            lateinit var id: String
        }
    }
}