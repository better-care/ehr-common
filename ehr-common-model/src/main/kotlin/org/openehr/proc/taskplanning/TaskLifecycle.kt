package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
enum class TaskLifecycle(val code: Int) : EnumerationInteger, EnumerationString {
    PLANNED(0),
    AVAILABLE(1),
    CANCELLED(2),
    ABANDONED(4),
    COMPLETED(8),
    SUSPENDED(6),
    UNDERWAY(5);
}