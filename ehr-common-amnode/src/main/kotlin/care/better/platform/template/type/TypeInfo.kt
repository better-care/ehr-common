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

package care.better.platform.template.type

/**
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Holds type information about the field on the parent.
 *
 * @constructor Creates new instance of [TypeInfo]
 * @param type [Class] or generic [Class] if field is a [Collection]
 * @param collection [CollectionInfo]
 */
data class TypeInfo(val type: Class<*>, val collection: CollectionInfo? = null) {

    /**
     * Checks if the field on the parent is a [Collection].
     *
     * @return [Boolean] indicating if the field on the parent is a [Collection]
     */
    fun isCollection(): Boolean = collection != null
}

/**
 * Holds the information about the collection.
 *
 * @constructor Creates new instance of [CollectionInfo]
 * @param collectionType [CollectionType]
 */
data class CollectionInfo(val collectionType: CollectionType)

/**
 * Enum of all possible collections in the RM model.
 */
enum class CollectionType {
    LIST, SET
}
