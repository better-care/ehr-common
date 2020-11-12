package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class SiblingOrder : AmObject(), Serializable {
    var isBefore = false

    @RequiresNotNull
    var siblingNodeId: String? = null
}