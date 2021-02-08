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
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

/**
 * @author Primoz Delopst
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OBJECT_REF", propOrder = [
    "id",
    "namespace",
    "type"])
@XmlSeeAlso(value = [PartyRef::class, AccessGroupRef::class, LocatableRef::class])
@Open
class ObjectRef : RmObject(), Serializable {

    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0L


        /**
         * Creates a person [ObjectRef].
         *
         * @param uid       uid
         * @param namespace namespace
         * @return [ObjectRef] object
         */
        @JvmStatic
        fun create(uid: String, namespace: String): ObjectRef = create("PERSON", uid, namespace)

        /**
         * Creates an [ObjectRef].
         *
         * @param type      type
         * @param uid       uid
         * @param namespace namespace
         * @return [ObjectRef] object
         */
        @JvmStatic
        fun create(type: String, uid: String, namespace: String): ObjectRef =
                ObjectRef().apply {
                    this.id = ObjectVersionId.create(uid)
                    this.namespace = namespace
                    this.type = type
                }
    }

    @XmlElement(required = true)
    @Required
    var id: ObjectId? = null

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter::class)
    @XmlSchemaType(name = "token")
    @Required
    var namespace: String? = null

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter::class)
    @XmlSchemaType(name = "token")
    @Required
    var type: String? = null
}
