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

import care.better.platform.jaxb.JaxbRegistry.Companion.createInstance
import care.better.tagging.dto.TagList
import care.better.tagging.dto.TagWithValueDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.am.aom.TermBindingItem
import org.openehr.am.aom.TermBindingSet
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.composition.Composition
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvDate
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.util.concurrent.Executors
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.namespace.QName
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
open class SerializationTest {
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

        val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller
        val stringWriter = StringWriter()
        marshaller.marshal(composition, stringWriter)
        val compositionString = stringWriter.toString()
        assertThat(compositionString).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
        assertThat(compositionString).contains("<uid xsi:type=\"OBJECT_VERSION_ID\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><value>f74c649e-3259-4b43-9943-67c86b8ee13a::default::1</value></uid>")
    }

    @Test
    @Throws(JAXBException::class)
    fun testTagSerialization() {
        val writer = StringWriter()
        val jaxbRegistry = createInstance(listOf("care.better.tagging"))
        val marshaller = jaxbRegistry.createMarshaller()
        val tagWithValueDto = TagWithValueDto("tag", "value", "/")
        marshaller.marshal(tagWithValueDto, writer)
        val xml = writer.toString()
        assertThat(xml).contains("<te:tag>tag</te:tag>")
        val unmarshaller = jaxbRegistry.createUnmarshaller()
        val returned = unmarshaller.unmarshal(StreamSource(StringReader(xml))) as TagWithValueDto
        assertThat(returned.tag).isEqualTo("tag")
        assertThat(returned.value).isEqualTo("value")
        assertThat(returned.getAqlPath()).isEqualTo("/")
    }

    @Test
    @Throws(JAXBException::class)
    fun testTagListSerialization() {
        val writer = StringWriter()
        val jaxbRegistry = createInstance(listOf("care.better.tagging"))
        val marshaller = jaxbRegistry.createMarshaller()
        val tagWithValueDto = TagWithValueDto("tag", "value", "/")
        val tagList = TagList(setOf(tagWithValueDto))
        marshaller.marshal(tagList, writer)
        val xml = writer.toString()
        assertThat(xml).contains("<te:tagList")
        assertThat(xml).contains("<te:tag>tag</te:tag>")
        val unmarshaller = jaxbRegistry.createUnmarshaller()
        val returned = unmarshaller.unmarshal(StreamSource(StringReader(xml))) as TagList
        assertThat(returned.tags!![0].tag).isEqualTo("tag")
        assertThat(returned.tags!![0].value).isEqualTo("value")
        assertThat(returned.tags!![0].getAqlPath()).isEqualTo("/")
    }

    @Test
    fun testDateWithJvmOverloadsSerialization() {
        val date = DvDate(value = "2020-01-01")

        val unmarshaller: Unmarshaller = JaxbRegistry.getInstance().unmarshaller
        val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller

        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("date"), DvDate::class.java, date), stringWriter)
        val rmString = stringWriter.toString()

        val date2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), DvDate::class.java).value
        assertThat(date2.value).isEqualTo("2020-01-01")

        val codePhrase = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        val stringWriter1 = StringWriter()
        marshaller.marshal(JAXBElement(QName("code_phrase"), CodePhrase::class.java, codePhrase), stringWriter1)
        val rmString1 = stringWriter1.toString()

        val codePhrase2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString1)), CodePhrase::class.java).value
        assertThat(codePhrase2.terminologyId).isNotNull
        assertThat(codePhrase2.codeString).isNotNull
    }

    @Test
    fun testCodePhraseWithJvmOverloadsSerialization() {
        val unmarshaller: Unmarshaller = JaxbRegistry.getInstance().unmarshaller
        val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller

        val codePhrase = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("code_phrase"), CodePhrase::class.java, codePhrase), stringWriter)
        val rmString = stringWriter.toString()

        val codePhrase2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), CodePhrase::class.java).value
        assertThat(codePhrase2.terminologyId).isNotNull
        assertThat(codePhrase2.codeString).isNotNull
    }

    @Test
    fun testTermBindingItemWithJvmOverloadsSerialization() {
        val termBindingItem = TermBindingItem().apply {
            code = "code"
            value = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        }

        val unmarshaller: Unmarshaller = JaxbRegistry.getInstance().unmarshaller
        val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller
        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("term_binding_item"), TermBindingItem::class.java, termBindingItem), stringWriter)
        val rmString = stringWriter.toString()

        val termBindingItem2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), TermBindingItem::class.java).value
        assertThat(termBindingItem2.code).isNotNull
        assertThat(termBindingItem2.value).isNotNull
    }

    @Test
    fun testTermBindingSetWithJvmOverloadsSerialization() {
        val termBindingItem = TermBindingItem().apply {
            code = "code"
            value = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        }
        val termBindingSet = TermBindingSet()
        termBindingSet.items.add(termBindingItem)
        termBindingSet.terminology = "terminology"

        val unmarshaller: Unmarshaller = JaxbRegistry.getInstance().unmarshaller
        val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller
        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("term_binding_set"), TermBindingSet::class.java, termBindingSet), stringWriter)
        val rmString = stringWriter.toString()

        val termBindingSet2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), TermBindingSet::class.java).value
        assertThat(termBindingSet2.terminology).isEqualTo("terminology")
        assertThat(termBindingSet2.items).isNotEmpty
        assertThat(termBindingSet2.items[0].code).isNotNull
        assertThat(termBindingSet2.items[0].value).isNotNull
    }

    @Test
    fun testParallelMarshalling() {
        val composition = getComposition("/composition.xml")
        val executor = Executors.newFixedThreadPool(20)

        IntRange(0, 1000)
            .map { Runnable { JaxbRegistry.getInstance().marshaller.marshal(composition, StringWriter()) } }
            .forEach(executor::submit)

        testCompositionSerialization()
    }

    @Test
    fun testParallelUnmarshalling() {
        val executor = Executors.newFixedThreadPool(20)

        IntRange(0, 1000)
            .map { Runnable { getComposition("/composition.xml") } }
            .forEach(executor::submit)

        testCompositionDeserialization()
    }

    @Throws(JAXBException::class, IOException::class)
    protected open fun getComposition(compositionFile: String): Composition =
        SerializationTest::class.java.getResourceAsStream(compositionFile).use { stream ->
            if (stream == null)
                throw RuntimeException("Composition resource was not found: $compositionFile.")
            else
                JaxbRegistry.getInstance().unmarshaller.unmarshal(StreamSource(stream), Composition::class.java).value
        }

}
