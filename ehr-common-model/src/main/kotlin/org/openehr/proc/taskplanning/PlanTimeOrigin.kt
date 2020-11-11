package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
enum class PlanTimeOrigin : EnumerationString, EnumerationInteger{
    CURRENT_WORK_PLAN,
    CURRENT_TASK_PLAN,
    INNER_REPEAT_SECTION,
    OUTER_REPEAT_SECTION;
}