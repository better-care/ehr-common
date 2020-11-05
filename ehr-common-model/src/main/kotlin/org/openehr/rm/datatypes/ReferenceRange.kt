package org.openehr.rm.datatypes

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ReferenceRange : RmObject() {
    lateinit var meaning: DvText
    lateinit var range: DvInterval
}