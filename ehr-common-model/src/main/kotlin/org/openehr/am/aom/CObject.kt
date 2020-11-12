package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

abstract class CObject : ArchetypeConstraint() {
    @RequiresNotNull
    var rmTypeName: String? = null

    @RequiresNotNull
    var occurrences: IntervalOfInteger? = null

    @RequiresNotNull
    var nodeId: String? = null
    var siblingOrder: SiblingOrder? = null
}