package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TView : AmObject(), Serializable {
    var constraints: MutableList<Constraints> = mutableListOf()

    class Constraints : AmObject(), Serializable {
        @RequiresNotEmpty
        var items: MutableList<Items> = mutableListOf()

        @RequiresNotNull
        var path: String? = null

        class Items : AmObject(), Serializable {
            @RequiresNotNull
            var value: Any? = null

            @RequiresNotNull
            var id: String? = null
        }
    }
}