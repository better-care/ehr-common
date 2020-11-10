package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ConditionBranch : ChoiceBranch<PlanItem>, ExpressionNamesProvider{
}