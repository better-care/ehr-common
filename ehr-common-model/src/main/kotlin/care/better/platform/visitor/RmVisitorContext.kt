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

package care.better.platform.visitor

import care.better.openehr.rm.RmObject
import org.openehr.rm.common.Locatable

interface RmVisitorContext {
    fun withLocatable(attributeName: String, locatable: Locatable, typeName: String, processProperties: (RmVisitorContext) -> Unit)
    fun withObject(attributeName: String, value: RmObject, typeName: String, processProperties: (RmVisitorContext) -> Unit)
    fun withCollection(attributeName: String, collection: Collection<*>, owner: Any, processProperties: (RmVisitorContext) -> Unit)
    fun visitValue(attributeName: String, value: Any, owner: Any)
}
