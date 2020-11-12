package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

abstract class CAttribute : ArchetypeConstraint() {
    @RequiresNotNull
    var rmAttributeName: String? = null

    @RequiresNotNull
    var existence: IntervalOfInteger? = null
    var differentialPath: String? = null
    var matchNegated = false
    var children: MutableList<CObject> = mutableListOf()
}