package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable
import java.util.LinkedHashMap

/**
 * @author Primoz Delopst
 */

@Serializable
class TaskPlanEventRecord : EventRecord(){
    val details: Map<String, String> = LinkedHashMap()
}