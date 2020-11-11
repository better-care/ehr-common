package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class StringDictionaryItem : RmObject(), Serializable {
    var value: String? = null
    lateinit var id: String
}