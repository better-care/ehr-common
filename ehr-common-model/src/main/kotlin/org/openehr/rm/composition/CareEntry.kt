package org.openehr.rm.composition

import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

abstract class CareEntry : Entry() {
    var protocol: ItemStructure? = null
    var guidelineId: ObjectRef? = null
}