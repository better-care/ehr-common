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
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TemplateId
import java.io.Serializable
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
    name = "ARCHETYPED", propOrder = [
        "archetypeId",
        "templateId",
        "rmVersion"])
@Open
class Archetyped constructor() : RmObject(), Serializable {

    @JvmOverloads
    constructor(archetypeId: ArchetypeId, templateId: TemplateId? = null, rmVersion: String = RM_VERSION.getVersion()) : this() {
        this.archetypeId = archetypeId
        this.templateId = templateId
        this.rmVersion = rmVersion
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "archetype_id", required = true)
    @Required
    var archetypeId: ArchetypeId? = null

    @XmlElement(name = "template_id")
    var templateId: TemplateId? = null

    @XmlElement(name = "rm_version", required = true)
    var rmVersion: String = RM_VERSION.getVersion()
}
