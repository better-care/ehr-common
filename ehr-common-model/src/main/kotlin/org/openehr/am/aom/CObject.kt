package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

abstract class CObject : ArchetypeConstraint() {
    lateinit var rmTypeName: String
    lateinit var occurrences: IntervalOfInteger
    lateinit var nodeId: String
    var siblingOrder: SiblingOrder? = null
}