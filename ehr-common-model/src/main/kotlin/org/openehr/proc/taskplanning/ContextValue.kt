package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor

/**
 * @author Primoz Delopst
 */
abstract class ContextValue<T>() : RmObject(), VisitableByModelVisitor {
    lateinit var name: String
    lateinit var type: ExprTypeDef<T>


    protected constructor(type: ExprTypeDef<T>) : this() {
        this.type = type
    }

    protected constructor(name: String, type: ExprTypeDef<T>) : this(type) {
        this.name = name
    }
}