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

package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.visitor.RmVisitorContext
import jakarta.xml.bind.annotation.*
import kotlinx.serialization.SerialName
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VERSIONED_OBJECT", namespace = "http://schemas.openehr.org/v1", propOrder = ["uid", "ownerId", "timeCreated", "trunkLifecycleState"])
@XmlRootElement(namespace = "http://schemas.openehr.org/v1")
@kotlinx.serialization.Serializable
@SerialName("VERSIONED_OBJECT")
class VersionedObject : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement
    var uid: HierObjectId? = null

    @XmlElement(name = "owner_id")
    @SerialName("owner_id")
    var ownerId: ObjectRef? = null

    @XmlElement(name = "time_created")
    @SerialName("time_created")
    var timeCreated: DvDateTime? = null

    @XmlElement(name = "trunk_lifecycle_state")
    @SerialName("trunk_lifecycle_state")
    var trunkLifecycleState: DvCodedText? = null

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "VERSIONED_OBJECT") {
            uid?.visit("uid", ctx)
            ownerId?.visit("owner_id", ctx)
            timeCreated?.visit("time_created", ctx)
            trunkLifecycleState?.visit("trunk_lifecycle_state", ctx)

        }
    }
}
