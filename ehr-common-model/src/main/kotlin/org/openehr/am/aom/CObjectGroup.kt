package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class CObjectGroup : ArchetypeConstraint() {
    lateinit var range: IntervalOfInteger
    lateinit var cardinality: Cardinality
    lateinit var occurrences: IntervalOfInteger
}