package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TAttribute : AmObject() {
    lateinit var rmAttributeName: String
    var children: MutableList<TComplexObject> = mutableListOf()
    lateinit var differentialPath: String
}