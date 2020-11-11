package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ReferenceRange : RmObject(), Serializable {
    lateinit var meaning: DvText
    lateinit var range: DvInterval
}