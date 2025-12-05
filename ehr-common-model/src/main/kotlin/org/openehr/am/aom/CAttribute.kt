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

package org.openehr.am.aom

import care.better.platform.annotation.Required
import care.better.platform.jaxb.JaxbModelConfiguration
import org.openehr.base.foundationtypes.IntervalOfInteger
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "C_ATTRIBUTE", propOrder = [
        "rmAttributeName",
        "existence",
        "differentialPath",
        "matchNegated",
        "children"])
@XmlSeeAlso(value = [CSingleAttribute::class, CMultipleAttribute::class])
abstract class CAttribute : ArchetypeConstraint() {
    companion object {
        private const val serialVersionUID: Long = 0L

    }

    @XmlElement(name = "rm_attribute_name", required = true)
    @Required
    var rmAttributeName: String? = null

    @XmlElement(required = true)
    @Required
    var existence: IntervalOfInteger? = null

    @XmlElement(name = "differential_path")
    var differentialPath: String? = null

    // Avoid serializing match_negated, but the field still exists to retain compatibility with XMLs serialized with older versions
    // This needs to be enabled with JabGlobalConfiguration.removeMatchNegatedOnSerialization,
    // as such templates will no longer be readable by older versions of the library
    @Deprecated("", level = DeprecationLevel.HIDDEN)
    @Suppress("unused")
    @get:XmlElement(name = "match_negated")
    var matchNegated: Boolean?
        get() = if (JaxbModelConfiguration.removeMatchNegatedOnSerialization) null else false
        set(value) {  }

    @XmlElement(type = CObject::class)
    var children: MutableList<CObject> = mutableListOf()
}
