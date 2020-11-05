package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
class Link : RmObject() {
    lateinit var meaning: DvText
    lateinit var type: DvText
    lateinit var target: DvEhrUri
}