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

//import care.better.platform.json.kotlin.serialization.polymorphism.RmObjectPolymorphicSerializersModuleProvider
import care.better.platform.json.kotlin.serialization.serializers.registerAdditionalJavaSerializers
import care.better.platform.json.kotlin.serialization.serializers.registerOpenEhrTemporalSerializers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

/**
 * @author Primoz Delopst
 * @since 4.2.2
 *
 * Capable of serializing RM model in a format defined by openEHR standard.
 *
 * @constructor Creates a new instance of [OpenEhrKotlinJsonSerializer]
 * @param serializerModules [List] of additional [SerializersModule] to include
 */
class OpenEhrKotlinJsonSerializer(private val serializerModules: List<SerializersModule> = emptyList()) {
    @OptIn(ExperimentalSerializationApi::class)
    val json: Json = Json {
        namingStrategy = OpenEhrPropertyNamingStrategy
        ignoreUnknownKeys = true
        classDiscriminatorMode = ClassDiscriminatorMode.ALL_JSON_OBJECTS
        serializersModule = SerializersModule {
//            include(RmObjectPolymorphicSerializersModuleProvider.module)
            registerOpenEhrTemporalSerializers()
            registerAdditionalJavaSerializers()
            serializerModules.forEach { include(it) }
        }
        classDiscriminator = "_type"
        encodeDefaults = true
    }
}
