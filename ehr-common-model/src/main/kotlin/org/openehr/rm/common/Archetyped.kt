package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TemplateId

/**
 * @author Primoz Delopst
 */

@Serializable
class Archetyped : RmObject() {
    lateinit var archetypeId: ArchetypeId
    var templateId: TemplateId? = null
    var rmVersion: String = RM_VERSION.version
}