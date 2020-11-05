package org.openehr.base.basetypes

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class ObjectRef : RmObject() {
    lateinit var id: ObjectId
    lateinit var namespace: String
    val type: String? = null
}