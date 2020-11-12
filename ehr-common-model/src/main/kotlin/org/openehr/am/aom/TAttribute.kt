package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TAttribute : AmObject(), Serializable {
    @RequiresNotNull
    var rmAttributeName: String? = null
    var children: MutableList<TComplexObject> = mutableListOf()

    @RequiresNotNull
    var differentialPath: String? = null
}