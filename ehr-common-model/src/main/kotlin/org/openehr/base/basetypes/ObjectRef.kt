package org.openehr.base.basetypes

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

open class ObjectRef : RmObject(), Serializable {
    @RequiresNotNull
    var id: ObjectId? = null

    @RequiresNotNull
    var namespace: String? = null
    val type: String? = null
}