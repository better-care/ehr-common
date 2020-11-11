package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TAttribute : AmObject(), Serializable {
    lateinit var rmAttributeName: String
    var children: MutableList<TComplexObject> = mutableListOf()
    lateinit var differentialPath: String
}