package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */
@Serializable
class ContextConstant<T : Any> : ContextValue<T> {
    lateinit var value: T
}