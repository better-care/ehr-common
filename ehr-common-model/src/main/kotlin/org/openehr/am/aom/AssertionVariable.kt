package org.openehr.am.aom

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class AssertionVariable : RmObject(), Serializable {
    @RequiresNotNull
    var name: String? = null

    @RequiresNotNull
    var definition: String? = null
}