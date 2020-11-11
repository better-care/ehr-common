package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class SiblingOrder : AmObject(), Serializable {
    var isBefore = false
    lateinit var siblingNodeId: String
}