package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class SiblingOrder : AmObject() {
    var isBefore = false
    lateinit var siblingNodeId: String
}