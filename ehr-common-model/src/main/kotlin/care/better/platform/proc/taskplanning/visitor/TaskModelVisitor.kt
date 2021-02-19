/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package care.better.platform.proc.taskplanning.visitor

import care.better.openehr.rm.RmObject
import org.openehr.proc.taskplanning.*
import org.openehr.rm.common.PartyProxy

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

interface TaskModelVisitor {
    fun visit(workPlan: WorkPlan)

    fun visit(planDataContext: PlanDataContext)

    fun visit(contextConstant: ContextConstant<*>)

    fun visit(contextVariable: ContextVariable<*>)

    fun visit(contextVariable: LocalVariable<*>)

    fun visit(contextVariable: ExternalVariable<*>)

    fun visit(contextVariable: StateVariable<*>)

    fun visit(contextVariable: EventVariable<*>)

    fun visit(contextVariable: ContinuousEventVariable<*>)

    fun visit(contextExpression: ContextExpression<*>)

    fun visit(contextExpression: BooleanContextExpression)

    fun visit(systemCall: SystemCall)

    fun visit(apiCall: ApiCall)

    fun visit(queryCall: QueryCall)

    fun visit(taskPlan: TaskPlan)

    fun visit(planItem: PlanItem)

    fun visit(taskGroup: TaskGroup<*>)

    fun visit(decisionGroup: DecisionGroup)

    fun visit(conditionGroup: ConditionGroup)

    fun visit(eventGroup: EventGroup)

    fun visit(decisionBranch: DecisionBranch)

    fun visit(conditionBranch: ConditionBranch)

    fun visit(eventBranch: EventBranch)

    fun visit(task: Task<*>)

    fun visit(taskAction: TaskAction)

    fun visit(dispatchableAction: DispatchableAction)

    fun visit(systemRequest: SystemRequest)

    fun visit(externalRequest: ExternalRequest)

    fun visit(handOff: HandOff)

    fun visit(performableAction: PerformableAction)

    fun visit(definedAction: DefinedAction)

    fun visit(subPlan: SubPlan)

    fun visit(partyProxy: PartyProxy)

    fun visit(taskParticipation: TaskParticipation)

    fun visit(subjectPrecondition: SubjectPrecondition)

    fun visit(reviewDatasetSpec: ReviewDatasetSpec)

    fun visit(captureDatasetSpec: CaptureDatasetSpec)

    fun visit(datasetSpec: DatasetSpec)

    fun visit(resourceParticipation: ResourceParticipation)

    fun visit(parameterDef: ParameterDef<*>)

    fun visit(parameterMapping: ParameterMapping)

    fun visit(choiceGroup: ChoiceGroup<*>)

    fun visit(choiceBranch: ChoiceBranch<*>)

    fun visit(adhocGroup: AdhocGroup)

    fun visit(adhocBranch: AdhocBranch)

    fun visit(performableTask: PerformableTask<*>)

    fun visit(dispatchableTask: DispatchableTask<*>)

    fun visit(orderRef: OrderRef)

    fun visit(datasetCommitGroup: DatasetCommitGroup)

    fun afterVisit(`object`: RmObject)

    fun afterAccept(`object`: RmObject)
}
