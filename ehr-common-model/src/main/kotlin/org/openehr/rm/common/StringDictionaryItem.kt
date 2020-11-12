package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class StringDictionaryItem : RmObject(), Serializable {
    var value: String? = null

    @RequiresNotNull
    var id: String? = null
}