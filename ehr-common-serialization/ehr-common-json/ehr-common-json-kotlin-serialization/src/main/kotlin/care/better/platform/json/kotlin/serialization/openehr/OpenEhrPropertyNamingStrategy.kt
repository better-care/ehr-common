/* Copyright 2025 Better Ltd (www.better.care)
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

package care.better.platform.json.kotlin.serialization.openehr

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonNamingStrategy

/**
 * @author Primoz Delopst
 * @since 4.2.2
 *
 * Implementation of [JsonNamingStrategy] for naming attributes of RM model in a format defined by openEHR.
 */
@OptIn(ExperimentalSerializationApi::class)
internal object OpenEhrPropertyNamingStrategy : JsonNamingStrategy {

    private val delegate: JsonNamingStrategy = JsonNamingStrategy.SnakeCase

    override fun serialNameForJson(descriptor: SerialDescriptor, elementIndex: Int, serialName: String): String {
        return if (descriptor.getElementAnnotations(elementIndex).isNotEmpty()) {
            serialName
        } else {
            delegate.serialNameForJson(descriptor, elementIndex, serialName)
        }
    }
}