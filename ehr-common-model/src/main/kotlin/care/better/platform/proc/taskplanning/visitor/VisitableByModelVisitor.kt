package care.better.platform.proc.taskplanning.visitor

/**
 * @author Primoz Delopst
 */

fun interface VisitableByModelVisitor {
    fun accept(visitor: TaskModelVisitor)
}