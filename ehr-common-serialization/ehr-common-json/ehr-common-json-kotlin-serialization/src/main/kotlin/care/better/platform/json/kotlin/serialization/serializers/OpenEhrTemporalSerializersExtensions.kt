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

package care.better.platform.json.kotlin.serialization.serializers

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrTemporal
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModuleBuilder
import java.time.temporal.Temporal

/**
 * @author Primoz Delopst
 * @since 4.2.2
 *
 * Set of extension functions to register contextual openEHR temporal serializers.
 */

internal fun SerializersModuleBuilder.registerOpenEhrTemporalSerializers() {
    contextual(OpenEhrTemporal::class, OpenEhrTemporalSerializer)
}

internal object OpenEhrTemporalSerializer : KSerializer<OpenEhrTemporal<out Temporal>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OpenEhrTemporal", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OpenEhrTemporal<out Temporal>) {
        encoder.encodeString(OpenEhrDateTimeFormatter.ofPattern("", false).format(value))
    }

    override fun deserialize(decoder: Decoder): OpenEhrTemporal<out Temporal> =
        throw UnsupportedOperationException("Deserializing OpenEhrTemporal is not supported.")
}