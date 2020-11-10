package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ClockTime : TimeSpecifier() {
    lateinit var time: String
}