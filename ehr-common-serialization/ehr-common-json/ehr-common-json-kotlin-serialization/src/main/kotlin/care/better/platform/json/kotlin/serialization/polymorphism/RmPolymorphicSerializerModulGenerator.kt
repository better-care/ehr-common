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

package care.better.platform.json.kotlin.serialization.polymorphism

import care.better.openehr.rm.RmObject
import care.better.platform.kotlin.serialization.polymorphism.api.GeneratePolymorphicSerializerModule
import kotlinx.serialization.modules.SerializersModule

/**
 * @author Primoz Delopst
 * @since 4.2.2
 *
 * Placeholder for generating RM model [SerializersModule]
 */
@Suppress("unused")
@GeneratePolymorphicSerializerModule(
    baseClass = RmObject::class,
    allowedPackages = ["org.openehr.base",
        "org.openehr.rm.datastructures",
        "org.openehr.rm.common",
        "org.openehr.rm.composition",
        "org.openehr.rm.datatypes",
        "org.openehr.rm.ehr",
        "org.openehr.rm.integration"])
class RmPolymorphicSerializerModulGenerator