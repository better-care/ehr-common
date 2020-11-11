package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class SubjectPrecondition() : RmObject(), Serializable, VisitableByModelVisitor, ExpressionNamesProvider {

    lateinit var description: String
    var expression: BooleanContextExpression? = null

    constructor(description: String) : this() {
        this.description = description
    }

    constructor(description: String, expression: BooleanContextExpression?) : this(description) {
        this.expression = expression
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        expression?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun getExpressionNames(): Sequence<String> = expression?.name?.let { listOf(it).asSequence() } ?: emptySequence()

    override fun toString(): String =
            "SubjectPrecondition{" +
                    "description='$description'" +
                    ", expression=$expression" +
                    '}'
}