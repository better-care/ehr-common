package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class StringDictionaryItem : RmObject() {
    var value: String? = null
    lateinit var id: String
}