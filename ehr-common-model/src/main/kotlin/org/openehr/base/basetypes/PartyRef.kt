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

import care.better.platform.annotation.Open
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PARTY_REF")
@Open
class PartyRef() : ObjectRef() {
    @JvmOverloads
    constructor(
            id: ObjectId,
            namespace: String? = null,
            type: String? = null) : this() {
        this.id = id
        this.namespace = namespace
        this.type = type
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }
}
