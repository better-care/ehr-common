package care.better.platform.jaxb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition
import org.openehr.rm.datatypes.DvDate
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.namespace.QName
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 */
open class SerializationTest {

    private val unmarshaller: Unmarshaller = JaxbRegistry.getInstance().unmarshaller
    private val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller

    @Test
    fun testCompositionDeserialization() {
        val composition = getComposition("/composition.xml")
        assertThat(composition).isNotNull
        assertThat(composition.content).isNotEmpty
        assertThat(composition.uid?.value).isEqualTo("f74c649e-3259-4b43-9943-67c86b8ee13a::default::1")
    }

    @Test
    fun testCompositionSerialization() {
        val composition = getComposition("/composition.xml")

        val stringWriter = StringWriter()
        marshaller.marshal(composition, stringWriter)
        val compositionString = stringWriter.toString()
        assertThat(compositionString).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
        assertThat(compositionString).contains("<uid xsi:type=\"OBJECT_VERSION_ID\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><value>f74c649e-3259-4b43-9943-67c86b8ee13a::default::1</value></uid>")
    }

    @Test
    fun testClassWithJvmOveraloadsSerialization() {
        val date = DvDate(value = "2020-01-01")

        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("date"), DvDate::class.java, date), stringWriter)
        val rmString = stringWriter.toString()

        val date2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), DvDate::class.java).value
        assertThat(date2.value).isEqualTo("2020-01-01")
    }

    @Throws(JAXBException::class, IOException::class)
    protected open fun getComposition(compositionFile: String): Composition =
        SerializationTest::class.java.getResourceAsStream(compositionFile).use { stream ->
            if (stream == null)
                throw RuntimeException("Composition resource was not found: $compositionFile.")
            else
                unmarshaller.unmarshal(StreamSource(stream), Composition::class.java).value
        }

}
