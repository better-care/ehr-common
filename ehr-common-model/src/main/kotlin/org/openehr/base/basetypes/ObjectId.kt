package org.openehr.base.basetypes

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class ObjectId : RmObject(), Serializable {
    @RequiresNotNull
    var value: String? = null
}


