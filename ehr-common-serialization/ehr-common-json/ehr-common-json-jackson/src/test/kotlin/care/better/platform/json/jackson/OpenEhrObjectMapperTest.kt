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

package care.better.platform.json.jackson

import care.better.openehr.rm.RmObject
import care.better.platform.json.jackson.openehr.OpenEhrObjectMapper
import care.better.platform.json.kotlin.serialization.openehr.OpenEhrKotlinJsonSerializer
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition
import java.io.IOException

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class OpenEhrObjectMapperTest {
    @Test
    fun testConvertToCompositionSimpleWithType() {
        val openEhrObjectMapper = OpenEhrObjectMapper().apply { this.enable(JsonParser.Feature.ALLOW_COMMENTS) }
        val composition: Composition = openEhrObjectMapper.readValue(OpenEhrObjectMapperTest::class.java.getResource("/simple-json.json"))
        assertThat(composition.name!!.value).isEqualTo("Report")
        composition.checkCompatibilityBetweenJacksonAndKotlinSerializations()
    }

    @Test
    @Throws(IOException::class)
    fun testConvertToCompositionSimpleWithoutType() {
        val openEhrObjectMapper = OpenEhrObjectMapper().apply { this.enable(JsonParser.Feature.ALLOW_COMMENTS) }
        val composition: Composition = openEhrObjectMapper.readValue(OpenEhrObjectMapperTest::class.java.getResource("/simple-json-without-type.json"))
        assertThat(composition.name?.value).isEqualTo("Report")
        composition.checkCompatibilityBetweenJacksonAndKotlinSerializations()
    }

    @Test
    @Throws(IOException::class)
    fun testConvertToCompositionWithType() {
        val openEhrObjectMapper = OpenEhrObjectMapper().apply { this.enable(JsonParser.Feature.ALLOW_COMMENTS) }
        val composition: Composition = openEhrObjectMapper.readValue(OpenEhrObjectMapperTest::class.java.getResource("/complex-json.json"))
        assertThat(composition.name?.value).isEqualTo("Report")
        assertThat(composition.context?.otherContext?.name?.value).isEqualTo("IZahl")
        composition.checkCompatibilityBetweenJacksonAndKotlinSerializations()
    }

    @Test
    @Throws(IOException::class)
    fun testConvertToCompositionWithoutType() {
        val openEhrObjectMapper = OpenEhrObjectMapper().apply { this.enable(JsonParser.Feature.ALLOW_COMMENTS) }
        val composition: Composition = openEhrObjectMapper.readValue(OpenEhrObjectMapperTest::class.java.getResource("/complex-json-without-type.json"))
        assertThat(composition.name?.value).isEqualTo("Report")
        assertThat(composition.context?.otherContext?.name?.value).isEqualTo("IZahl")
        composition.checkCompatibilityBetweenJacksonAndKotlinSerializations()
    }

    private inline fun <reified T : RmObject> T.checkCompatibilityBetweenJacksonAndKotlinSerializations(): T {
        val openEhrObjectMapper = OpenEhrObjectMapper().apply { this.enable(JsonParser.Feature.ALLOW_COMMENTS) }
        val rmObjectJson = openEhrObjectMapper.writeValueAsString(this)
        val rmObjectJsonUsingKotlinSerialization = OpenEhrKotlinJsonSerializer().json.encodeToString<T>(this)
        val rmObjectFromJson: T = openEhrObjectMapper.readValue(rmObjectJsonUsingKotlinSerialization)
        val rmObjectFromJsonUsingKotlinSerialization: T = OpenEhrKotlinJsonSerializer().json.decodeFromString(rmObjectJson)

        assertThat(this).usingRecursiveComparison().isEqualTo(rmObjectFromJson)
        assertThat(rmObjectJson).isEqualTo(rmObjectJsonUsingKotlinSerialization)
        assertThat(rmObjectFromJson).usingRecursiveComparison().isEqualTo(rmObjectFromJsonUsingKotlinSerialization)
        return this
    }
}
