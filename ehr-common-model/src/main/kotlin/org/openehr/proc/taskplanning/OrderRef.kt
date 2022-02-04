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

package org.openehr.proc.taskplanning

import care.better.platform.annotation.Open
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.common.Locatable
import java.io.Serializable
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ORDER_REF", propOrder = [
        "orderTag",
        "instructionArchetypeId",
        "actionArchetypeId",
        "orderRef"])
@Open
class OrderRef : Locatable(), Serializable, VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElements(value = [XmlElement(required = true, name = "orderTag"), XmlElement(required = true, name = "order_tag")])
    @XmlJavaTypeAdapter(CollapsedStringAdapter::class)
    @XmlSchemaType(name = "token")
    var orderTag: String? = null

    @XmlElements(value = [XmlElement(name = "instructionArchetypeId"), XmlElement(name = "instruction_archetype_id")])
    @XmlJavaTypeAdapter(CollapsedStringAdapter::class)
    @XmlSchemaType(name = "token")
    var instructionArchetypeId: String? = null

    @XmlElements(value = [XmlElement(name = "actionArchetypeId"), XmlElement(name = "action_archetype_id")])
    @XmlJavaTypeAdapter(CollapsedStringAdapter::class)
    @XmlSchemaType(name = "token")
    var actionArchetypeId: String? = null

    @XmlElement(name = "order_ref")
    var orderRef: LocatableRef? = null

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "OrderRef{" +
                "orderTag='$orderTag'" +
                ", instructionArchetypeId='$instructionArchetypeId'" +
                ", actionArchetypeId='$actionArchetypeId'" +
                ", orderRef=$orderRef" +
                "} ${super.toString()}"
}
