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

package org.openehr.base.basetypes

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import java.io.Serializable
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OBJECT_ID", propOrder = ["value"])
@XmlSeeAlso(value = [ArchetypeId::class, TemplateId::class, TerminologyId::class, UidBasedId::class, GenericId::class])
@Open
abstract class ObjectId : RmObject(), Serializable {

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter::class)
    @XmlSchemaType(name = "token")
    @Required
    var value: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> (other as ObjectId).value == value
        }

    override fun hashCode(): Int = Objects.hash(value)
}


