package org.openehr.base.basetypes

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class ObjectId : RmObject(), Serializable {
    lateinit var value: String
}


