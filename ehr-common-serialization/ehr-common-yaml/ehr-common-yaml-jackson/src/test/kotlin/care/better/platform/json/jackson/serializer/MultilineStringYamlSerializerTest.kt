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

package care.better.platform.json.jackson.serializer

import care.better.platform.yaml.jackson.serializer.MultilineStringYamlSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import java.io.StringWriter


/**
 * @author Jan Živković
 */
class MultilineStringYamlSerializerTest {

    companion object {
        private const val YAML_LITERAL_STYLE_WITHOUT_INDENT = "|-"
    }

    private val yamlObjectMapper = ObjectMapper(
            YAMLFactory()
                .enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE)
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
    ).registerKotlinModule()

    private val jsonObjectMapper = ObjectMapper().registerKotlinModule()

    @Test
    fun testSimpleStringFromYamlToYaml() {
        val string = """
            name: "testName"
            description: "singleLineTestDescription"${"\n"}
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).doesNotContain(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo(string)
    }

    @Test
    fun testSimpleStringToMultiLineStringFromYamlToYaml() {
        val string = """
            name: "testName"
            description: "line1\nline2\nline3"
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1
              line2
              line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testSimpleStringToMultiLineStringWithSpacesFromYamlToYaml() {
        val string = """
            name: "testName"
            description: "line1  \nline2\n  line3"
        """
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1
              line2
                line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testSimpleStringToMultiLineStringWithTabsFromYamlToYaml() {
        val string = """
            name: "testName"
            description: "line1\tafter tab\n\tindented line2\nline3\t"
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)
        assertThat(obj.description).contains("\t")

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1  after tab
                indented line2
              line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testSimpleStringToMultiLineStringWithCarriageReturnFromYamlToYaml() {
        val string = """
            name: "testName"
            description: "line1\r\nline2\r\nline3"
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)
        assertThat(obj.description).contains("\r")

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1
              line2
              line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testMultiLineStringFromYamlToYaml() {
        val string = """
            name: "testName"
            description: |-
              line1
              line2
              line3${"\n"}
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains("|-")
        assertThat(asString).isEqualTo(string)
    }

    @Test
    fun testMultiLineStringWithSpacesFromYamlToYaml() {
        val string = """
            name: "testName"
            description: |-
              line1  
              line2 
              line3  ${"\n"}
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1
              line2
              line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testMultiLineStringWithTabsFromYamlToYaml() {
        val string = """
            name: "testName"
            description: |-
              line1${"\t"}after tab
              ${"\t"}indented line2
              line3${"\n"}
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)
        assertThat(obj.description).contains("\t")

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1  after tab
                indented line2
              line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testComplexMultiLineStringFromYamlToYaml() {
        val string = """
            name: "testName"
            description: |-
              line1${"\t"}after tab
              ${"\t"}indented line2
              ${"\t"}${"\t"}line3
              line4 with trailing spaces   
              line5 with trailing tabs${"\t"}${"\t"}${"\t"}
              line6 2 empty lines below (one defined, one from kotlin multiline)
              ${"\n"}
              line9${"\n"}
        """.trimIndent()
        val obj: MultiLineObject = yamlObjectMapper.readValue(string)
        assertThat(obj.description).contains("\t")

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1  after tab
                indented line2
                  line3
              line4 with trailing spaces
              line5 with trailing tabs
              line6 2 empty lines below (one defined, one from kotlin multiline)
            
            
              line9${"\n"}
        """.trimIndent())
    }

    @Test
    fun testComplexMultiLineStringFromJsonToYaml() {
        val string = "{\"name\": \"testName\",\"description\": \"line1\\tafter tab\\n\\tindented line2\\n\\t\\tline3\\nline4 with trailing spaces   \\nline5 with trailing tabs\\t\\t\\t\\nline6 2 empty lines below\\n\\n\\nline9 with carriage return\\r\\nline10\"}"
        val obj: MultiLineObject = jsonObjectMapper.readValue(string)
        assertThat(obj.description).contains("\t")
        assertThat(obj.description).contains("\r")

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1  after tab
                indented line2
                  line3
              line4 with trailing spaces
              line5 with trailing tabs
              line6 2 empty lines below


              line9 with carriage return
              line10${"\n"}
        """.trimIndent())
    }

    @Test
    fun testSingleLineStringFromJsonToYamlTransformsToMultiLineString() {
        val string = "{\"name\": \"testName\",\"description\": \"line1\\nline2\\nline3\"}"
        val obj: SingleLineObject = jsonObjectMapper.readValue(string)

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).contains(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: |-
              line1
              line2
              line3${"\n"}
        """.trimIndent())
    }

    @Test
    fun testComplexSingleLineStringFromJsonToYamlStaysSingleLineString() {
        val string = "{\"name\": \"testName\",\"description\": \"line1\\r\\nline2\\tafter tab \\nline3\"}"
        val obj: SingleLineObject = jsonObjectMapper.readValue(string)
        assertThat(obj.description).contains("\t")
        assertThat(obj.description).contains("\r")

        val asString: String = yamlObjectMapper.writeValueAsString(obj)
        assertThat(asString).doesNotContain(YAML_LITERAL_STYLE_WITHOUT_INDENT)
        assertThat(asString).isEqualTo("""
            name: "testName"
            description: "line1\r\nline2\tafter tab \nline3"${"\n"}
        """.trimIndent())
    }

    @Test
    fun testMultiLineStringYamlSerializerDidNotProcessForJsonSerializer() {
        val serializer = spyk<MultilineStringYamlSerializer>(recordPrivateCalls = true)
        val jsonGenerator = jsonObjectMapper.createGenerator(StringWriter())
        val serializerProvider = mockk<SerializerProvider>()
        every {
            serializerProvider.defaultSerializeValue(any(), any())
        } just Runs

        serializer.serialize("", jsonGenerator, serializerProvider)
        serializer.serializeWithType("", jsonGenerator, serializerProvider, mockk())

        verifyCount {
            1 * { serializer.serialize(any(), jsonGenerator, serializerProvider) }
            1 * { serializer.serializeWithType(any(), jsonGenerator, serializerProvider, any()) }
            2 * { serializer["serializeInternal"](any<String>(), jsonGenerator, any<SerializerProvider>()) }
            0 * { serializer["preprocessString"](any<String>()) }
        }
    }

    @Test
    fun testMultiLineStringYamlSerializerDidProcessForYamlSerializer() {
        val serializer = spyk<MultilineStringYamlSerializer>(recordPrivateCalls = true)
        val yamlGenerator = yamlObjectMapper.createGenerator(StringWriter())
        val serializerProvider = mockk<SerializerProvider>()
        every {
            serializerProvider.defaultSerializeValue(any(), any())
        } just Runs

        serializer.serialize("", yamlGenerator, serializerProvider)
        serializer.serializeWithType("", yamlGenerator, serializerProvider, mockk())

        verifyCount {
            1 * { serializer.serialize(any(), yamlGenerator, serializerProvider) }
            1 * { serializer.serializeWithType(any(), yamlGenerator, serializerProvider, any()) }
            2 * { serializer["serializeInternal"](any<String>(), yamlGenerator, any<SerializerProvider>()) }
            2 * { serializer["preprocessString"](any<String>()) }
        }
    }
}

data class SingleLineObject(
        val name: String,
        val description: String
)

data class MultiLineObject(
        val name: String,
        @JsonSerialize(using = MultilineStringYamlSerializer::class)
        val description: String
)