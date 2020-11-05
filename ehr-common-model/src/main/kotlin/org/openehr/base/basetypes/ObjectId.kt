package org.openehr.base.basetypes

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class ObjectId : RmObject() {
    lateinit var value: String
}


