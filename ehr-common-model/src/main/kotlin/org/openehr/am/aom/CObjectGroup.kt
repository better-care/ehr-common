package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

class CObjectGroup : ArchetypeConstraint() {
    lateinit var range: IntervalOfInteger
    lateinit var cardinality: Cardinality
    lateinit var occurrences: IntervalOfInteger
}