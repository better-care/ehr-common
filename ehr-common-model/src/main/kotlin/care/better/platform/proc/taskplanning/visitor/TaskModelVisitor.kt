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
    fun visit(workPlan: WorkPlan): Boolean

    fun visit(planDataContext: PlanDataContext): Boolean

    fun visit(contextConstant: ContextConstant<*>): Boolean

    fun visit(contextVariable: ContextVariable<*>): Boolean

    fun visit(contextVariable: LocalVariable<*>): Boolean

    fun visit(contextVariable: ExternalVariable<*>): Boolean

    fun visit(contextVariable: StateVariable<*>): Boolean

    fun visit(contextVariable: EventVariable<*>): Boolean

    fun visit(contextVariable: ContinuousEventVariable<*>): Boolean

    fun visit(contextExpression: ContextExpression<*>): Boolean

    fun visit(contextExpression: BooleanContextExpression): Boolean

    fun visit(systemCall: SystemCall): Boolean

    fun visit(apiCall: ApiCall): Boolean

    fun visit(queryCall: QueryCall): Boolean

    fun visit(taskPlan: TaskPlan): Boolean

    fun visit(planItem: PlanItem): Boolean

    fun visit(taskGroup: TaskGroup<*>): Boolean

    fun visit(decisionGroup: DecisionGroup): Boolean

    fun visit(conditionGroup: ConditionGroup): Boolean

    fun visit(eventGroup: EventGroup): Boolean

    fun visit(decisionBranch: DecisionBranch): Boolean

    fun visit(conditionBranch: ConditionBranch): Boolean

    fun visit(eventBranch: EventBranch): Boolean

    fun visit(task: Task<*>): Boolean

    fun visit(taskAction: TaskAction): Boolean

    @Suppress("SpellCheckingInspection")
    fun visit(dispatchableAction: DispatchableAction): Boolean

    fun visit(systemRequest: SystemRequest): Boolean

    fun visit(externalRequest: ExternalRequest): Boolean

    fun visit(handOff: HandOff): Boolean

    fun visit(performableAction: PerformableAction): Boolean

    fun visit(definedAction: DefinedAction): Boolean

    fun visit(subPlan: SubPlan): Boolean

    fun visit(partyProxy: PartyProxy): Boolean

    fun visit(taskParticipation: TaskParticipation): Boolean

    fun visit(subjectPrecondition: SubjectPrecondition): Boolean

    fun visit(reviewDatasetSpec: ReviewDatasetSpec): Boolean

    fun visit(captureDatasetSpec: CaptureDatasetSpec): Boolean

    fun visit(datasetSpec: DatasetSpec): Boolean

    fun visit(resourceParticipation: ResourceParticipation): Boolean

    fun visit(parameterDef: ParameterDef<*>): Boolean

    fun visit(parameterMapping: ParameterMapping): Boolean

    fun visit(choiceGroup: ChoiceGroup<*>): Boolean

    fun visit(choiceBranch: ChoiceBranch<*>): Boolean

    fun visit(adhocGroup: AdhocGroup): Boolean

    fun visit(adhocBranch: AdhocBranch): Boolean

    fun visit(performableTask: PerformableTask<*>): Boolean

    @Suppress("SpellCheckingInspection")
    fun visit(dispatchableTask: DispatchableTask<*>): Boolean

    fun visit(orderRef: OrderRef): Boolean

    fun visit(datasetCommitGroup: DatasetCommitGroup): Boolean

    fun afterVisit(`object`: RmObject)

    fun afterAccept(`object`: RmObject)
}
