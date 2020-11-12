/* Copyright 2020-2025 Better Ltd (www.better.care)
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
import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ResourceDescription : RmObject(), Serializable {
    @RequiresNotEmpty
    var originalAuthor: MutableList<StringDictionaryItem> = mutableListOf()
    var otherContributors: MutableList<String> = mutableListOf()

    @RequiresNotNull
    var lifecycleState: String? = null
    var resourcePackageUri: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()

    @RequiresNotEmpty
    var details: MutableList<ResourceDescriptionItem> = mutableListOf()
    var parentResource: AuthoredResource? = null
}