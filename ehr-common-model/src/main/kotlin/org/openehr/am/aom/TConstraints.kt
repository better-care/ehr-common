package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TConstraints : AmObject() {
    var attributes: MutableList<TAttribute> = mutableListOf()
}