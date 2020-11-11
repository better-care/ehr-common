package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Link : RmObject(), Serializable {
    lateinit var meaning: DvText
    lateinit var type: DvText
    lateinit var target: DvEhrUri
}