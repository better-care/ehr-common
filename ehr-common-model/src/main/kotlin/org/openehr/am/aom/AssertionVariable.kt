package org.openehr.am.aom

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class AssertionVariable : RmObject() {
    lateinit var name: String
    lateinit var definition: String
}