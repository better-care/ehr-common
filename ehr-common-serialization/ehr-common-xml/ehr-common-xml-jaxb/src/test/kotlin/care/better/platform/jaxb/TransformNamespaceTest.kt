package care.better.platform.jaxb

import care.better.platform.jaxb.JaxbRegistry.Companion.OPENEHR_NAMESPACE_V1
import care.better.platform.jaxb.JaxbRegistry.Companion.OPENEHR_NAMESPACE_V2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.am.aom.Template
import org.openehr.rm.composition.Composition
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import jakarta.xml.bind.JAXBException
import javax.xml.transform.stream.StreamSource

/**
 * @author Marko Pipan
 */
class TransformNamespaceTest {

    @Test
    fun readWithNamespaceV1() {
        val template = loadTemplate("namespace_v1.opt")
        assertThat(template.definition!!.attributes.map { it.rmAttributeName }).containsExactly("category", "context", "content")
    }

    @Test
    fun readCompositionWithNamespaceV1() {
        val composition = parseComposition(openResource("composition_lab_report.xml").reader(StandardCharsets.UTF_8))
        assertThat(composition.name?.value).isEqualTo("Laboratory report")
    }

    @Test
    fun readCompositionWithNamespaceV2() {
        val composition = parseComposition(openResource("composition_lab_report_v2.xml").reader(StandardCharsets.UTF_8))
        assertThat(composition.name?.value).isEqualTo("Laboratory report")
    }

    @Test
    fun readWithNamespaceV2() {
        val template = loadTemplate("namespace_v2.opt")
        assertThat(template.definition!!.attributes.map { it.rmAttributeName }).containsExactly("category", "context", "content")
    }

    @Test
    fun serializeWithNamespaceV1() {
        val template = loadTemplate("namespace_v1.opt")
        JaxbRegistry.serializeWithNamespace = OPENEHR_NAMESPACE_V1
        val templateString = serializeTemplate(template)
        assertThat(templateString).contains(OPENEHR_NAMESPACE_V1).doesNotContain(OPENEHR_NAMESPACE_V2)

        val template2 = deserializeTemplate(templateString)
        assertThat(template2.definition!!.attributes.map { it.rmAttributeName }).containsExactly("category", "context", "content")
    }

    @Test
    fun serializeWithNamespaceV2() {
        val template = loadTemplate("namespace_v1.opt")
        JaxbRegistry.serializeWithNamespace = OPENEHR_NAMESPACE_V2
        val templateString = serializeTemplate(template)
        assertThat(templateString).contains(OPENEHR_NAMESPACE_V2).doesNotContain(OPENEHR_NAMESPACE_V1)

        val template2 = deserializeTemplate(templateString)
        assertThat(template2.definition!!.attributes.map { it.rmAttributeName }).containsExactly("category", "context", "content")
    }

    private val jaxbRegistry =
        try {
            JaxbRegistry.getInstance()
        } catch (e: JAXBException) {
            throw IllegalStateException("Error creating JAXB context", e)
        }

    fun loadTemplate(resourceName: String): Template = parse(openResource(resourceName))

    fun openResource(resourceName: String): InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourceName) ?: throw IllegalArgumentException("Resource $resourceName not found")

    fun deserializeTemplate(templateString: String): Template = parse(templateString.byteInputStream(StandardCharsets.UTF_8))

    fun serializeTemplate(template: Template): String {
        val marshaller = jaxbRegistry.createMarshaller()
        val writer = StringWriter()
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
    private fun parseComposition(reader: Reader): Composition {
        try {
            return jaxbRegistry.createUnmarshaller().unmarshal(reader) as Composition
        } catch (e: JAXBException) {
            throw IllegalStateException("Error parsing composition!", e)
        }
    }
}
