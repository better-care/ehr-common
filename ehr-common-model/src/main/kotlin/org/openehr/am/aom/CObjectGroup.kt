package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

class CObjectGroup : ArchetypeConstraint() {
    @RequiresNotNull
    var range: IntervalOfInteger? = null

    @RequiresNotNull
    var cardinality: Cardinality? = null

    @RequiresNotNull
    var occurrences: IntervalOfInteger? = null
}