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

package care.better.platform.jaxb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition
import java.io.IOException
import java.io.StringWriter
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 * @since 3.1.0
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

    @Throws(JAXBException::class, IOException::class)
    protected open fun getComposition(compositionFile: String): Composition =
        SerializationTest::class.java.getResourceAsStream(compositionFile).use { stream ->
            if (stream == null)
                throw RuntimeException("Composition resource was not found: $compositionFile.")
            else
                unmarshaller.unmarshal(StreamSource(stream), Composition::class.java).value
        }

}
