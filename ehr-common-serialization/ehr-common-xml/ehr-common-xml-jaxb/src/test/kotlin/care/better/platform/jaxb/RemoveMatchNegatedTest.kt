package care.better.platform.jaxb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.am.aom.Template
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import jakarta.xml.bind.JAXBException
import javax.xml.transform.stream.StreamSource

/**
 * @author Marko Pipan
 */
class RemoveMatchNegatedTest {


    @Test
    fun readWithMatchNegated() {
        val template = loadTemplate("with_match_negated.opt")
        assertThat(template.definition!!.attributes.map { it.rmAttributeName }).containsExactly("category", "context", "content")
    }

    @Test
    fun readWithoutMatchNegated() {
        val template = loadTemplate("without_match_negated.opt")
        assertThat(template.definition!!.attributes.map { it.rmAttributeName }).containsExactly("category", "context", "content")
    }

    @Test
    fun serializeWithMatchNegated() {
        JaxbModelConfiguration.removeMatchNegatedOnSerialization = false
        val template = loadTemplate("with_match_negated.opt")
        val templateString = serializeTemplate(template)

        assertThat(templateString)
            .contains("<rm_attribute_name>")
            .contains("<match_negated>")
    }

    @Test
    fun serializeWithoutMatchNegated() {
        JaxbModelConfiguration.removeMatchNegatedOnSerialization = true
        val template = loadTemplate("with_match_negated.opt")
        val templateString = serializeTemplate(template)

        assertThat(templateString)
            .contains("<rm_attribute_name>")
            .doesNotContain("<match_negated>")
    }

    private val jaxbRegistry =
        try {
            JaxbRegistry.getInstance()
        } catch (e: JAXBException) {
            throw IllegalStateException("Error creating JAXB context", e)
        }

    fun loadTemplate(resourceName: String): Template = parse(Thread.currentThread().contextClassLoader.getResourceAsStream(resourceName))

    fun serializeTemplate(template: Template): String {
        val marshaller = jaxbRegistry.createMarshaller()
        val writer = java.io.StringWriter()
        marshaller.marshal(template, writer)
        return writer.toString()
    }

    private fun parse(inputStream: InputStream): Template {
        try {
            InputStreamReader(inputStream, StandardCharsets.UTF_8).use { reader ->
                return jaxbRegistry.createUnmarshaller().unmarshal(StreamSource(reader), Template::class.java).value
            }
        } catch (e: JAXBException) {
            throw IllegalStateException("Error parsing template!", e)
        }
    }
}
