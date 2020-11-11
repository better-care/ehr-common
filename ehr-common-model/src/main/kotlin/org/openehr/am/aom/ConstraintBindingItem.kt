package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ConstraintBindingItem : AmObject(), Serializable {
    lateinit var value: String
    lateinit var code: String
}