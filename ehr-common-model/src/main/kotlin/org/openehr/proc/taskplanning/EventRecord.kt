package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class EventRecord : RmObject() {
    lateinit var time: String
    var description: String? = null
}