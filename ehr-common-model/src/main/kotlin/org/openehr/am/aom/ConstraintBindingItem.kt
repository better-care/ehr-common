package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ConstraintBindingItem : AmObject(), Serializable {
    @RequiresNotNull
    var value: String? = null

    @RequiresNotNull
    var code: String? = null
}