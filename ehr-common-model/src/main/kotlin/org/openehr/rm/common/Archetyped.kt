package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TemplateId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Archetyped : RmObject(), Serializable {
    @RequiresNotNull
    var archetypeId: ArchetypeId? = null
    var templateId: TemplateId? = null
    var rmVersion: String = RM_VERSION.version
}