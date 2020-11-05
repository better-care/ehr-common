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

package org.openehr.rm.datatypes

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DV_IDENTIFIER", propOrder = [
        "issuer",
        "assigner",
        "id",
        "type"])
@Open
class DvIdentifier() : DataValue() {
    @JvmOverloads
    constructor(
            id: String,
            issuer: String? = null,
            assigner: String? = null,
            type: String? = null) : this() {
        this.id = id
        this.issuer = issuer
        this.assigner = assigner
        this.type = type
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var issuer: String? = null

    var assigner: String? = null

    @XmlElement(required = true)
    @Required
    var id: String? = null

    var type: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            (other as DvIdentifier).id != id -> false
            other.assigner != assigner -> false
            other.issuer != issuer -> false
            else -> other.type == type
        }

    override fun hashCode(): Int = Objects.hash(id, type, issuer, assigner)
}
