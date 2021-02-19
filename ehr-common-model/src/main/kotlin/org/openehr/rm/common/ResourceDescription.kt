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
    name = "RESOURCE_DESCRIPTION", propOrder = [
        "originalAuthor",
        "otherContributors",
        "lifecycleState",
        "resourcePackageUri",
        "otherDetails",
        "details",
        "parentResource"])
@Open
class ResourceDescription : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "original_author", required = true)
    @Required
    var originalAuthor: MutableList<StringDictionaryItem> = mutableListOf()

    @XmlElement(name = "other_contributors")
    var otherContributors: MutableList<String> = mutableListOf()

    @XmlElement(name = "lifecycle_state", required = true)
    @Required
    var lifecycleState: String? = null

    @XmlElement(name = "resource_package_uri")
    var resourcePackageUri: String? = null

    @XmlElement(name = "other_details")
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()

    @XmlElement(required = true)
    @Required
    var details: MutableList<ResourceDescriptionItem> = mutableListOf()

    @XmlElement(name = "parent_resource")
    var parentResource: AuthoredResource? = null
}
