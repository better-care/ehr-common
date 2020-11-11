package org.openehr.am.aom

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class AssertionVariable : RmObject(), Serializable {
    lateinit var name: String
    lateinit var definition: String
}