package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class CareEntry : Entry() {
    var protocol: ItemStructure? = null
    var guidelineId: ObjectRef? = null
}