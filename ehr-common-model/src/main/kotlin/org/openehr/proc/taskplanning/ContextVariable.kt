package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

abstract class ContextVariable<T>() : ContextValue<T>() {

    override fun accept(visitor: TaskModelVisitor) {
        TODO("Not yet implemented")
    }
}