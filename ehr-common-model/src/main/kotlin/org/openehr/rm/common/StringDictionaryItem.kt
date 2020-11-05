package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class StringDictionaryItem : RmObject() {
    var value: String? = null
    lateinit var id: String
}