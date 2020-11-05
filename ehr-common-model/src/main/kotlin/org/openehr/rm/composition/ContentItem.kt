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

package org.openehr.rm.composition

import org.openehr.proc.taskplanning.TaskPlan
import org.openehr.proc.taskplanning.WorkPlan
import org.openehr.rm.common.Locatable
import org.openehr.rm.integration.GenericEntry
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONTENT_ITEM")
@XmlSeeAlso(value = [Section::class, GenericEntry::class, Entry::class, WorkPlan::class, TaskPlan::class])
abstract class ContentItem : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }
}
