package org.openehr.base.basetypes

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class ObjectId : RmObject() {
    lateinit var value: String
}


