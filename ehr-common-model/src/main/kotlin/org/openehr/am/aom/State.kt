package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class State : AmObject(), Serializable {
    @RequiresNotNull
    var name: String? = null
}