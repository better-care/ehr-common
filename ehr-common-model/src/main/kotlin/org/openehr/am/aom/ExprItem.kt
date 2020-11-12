package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class ExprItem : AmObject(), Serializable {
    @RequiresNotNull
    var type: String? = null
}