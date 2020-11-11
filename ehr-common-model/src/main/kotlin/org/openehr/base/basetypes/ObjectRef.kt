package org.openehr.base.basetypes

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

open class ObjectRef : RmObject(), Serializable {
    lateinit var id: ObjectId
    lateinit var namespace: String
    val type: String? = null
}