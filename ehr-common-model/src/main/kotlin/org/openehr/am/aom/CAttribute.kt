package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class CAttribute : ArchetypeConstraint() {
    lateinit var rmAttributeName: String
    lateinit var existence: IntervalOfInteger
    var differentialPath: String? = null
    var matchNegated = false
    var children: MutableList<CObject> = mutableListOf()
}