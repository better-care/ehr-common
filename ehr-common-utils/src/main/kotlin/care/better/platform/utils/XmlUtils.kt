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

package care.better.platform.utils

import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.SAXNotRecognizedException
import org.xml.sax.SAXNotSupportedException
import java.io.*
import java.nio.charset.StandardCharsets
import javax.xml.XMLConstants
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.sax.SAXSource

/**
 * @author Dusan Markovic
 * @author Primoz Delopst
 * @since 3.1.0
 */

object XmlUtils {
    private const val XML_START_CHAR = '<'.code
    const val DEFAULT_SAX_PARSER_IMPL = "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
    const val DEFAULT_DOC_BUILDER_IMPL = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"

    @JvmStatic
    @Throws(ParserConfigurationException::class)
    fun createDocumentBuilder(): DocumentBuilder = createDocumentBuilderFactory().newDocumentBuilder()

    @JvmStatic
    @Throws(ParserConfigurationException::class)
    fun createDocumentBuilderFactory(): DocumentBuilderFactory =
        DocumentBuilderFactory.newInstance(DEFAULT_DOC_BUILDER_IMPL, null).apply {
            this.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            this.setFeature("http://xml.org/sax/features/external-general-entities", false)
            this.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            this.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            this.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
            this.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "")
            this.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "")
            this.isExpandEntityReferences = false
            this.isNamespaceAware = true
        }

    @JvmStatic
    @Throws(SAXNotSupportedException::class, SAXNotRecognizedException::class, ParserConfigurationException::class)
    fun createSAXParserFactory(): SAXParserFactory =
        SAXParserFactory.newInstance(DEFAULT_SAX_PARSER_IMPL, null).apply {
            this.setFeature("http://xml.org/sax/features/external-general-entities", false)
            this.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            this.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            this.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
            this.isNamespaceAware = true
        }


    @JvmStatic
    @Throws(JAXBException::class, ParserConfigurationException::class, SAXException::class, IOException::class)
    fun <T> unmarshal(saxParserFactory: SAXParserFactory, unmarshaller: Unmarshaller, stringToUnmarshall: String, declaredType: Class<T>): JAXBElement<T> =
        StringReader(stringToUnmarshall).use { reader ->
            unmarshallFromReader(saxParserFactory, unmarshaller, declaredType, reader)
        }

    @JvmStatic
    @Throws(JAXBException::class, ParserConfigurationException::class, SAXException::class, IOException::class)
    fun <T> unmarshal(
            saxParserFactory: SAXParserFactory,
            unmarshaller: Unmarshaller,
            inputStreamToUnmarshall: InputStream?,
            declaredType: Class<T>): JAXBElement<T> =
        InputStreamReader(skipToXmlStart(inputStreamToUnmarshall), StandardCharsets.UTF_8).use { reader ->
            unmarshallFromReader(saxParserFactory, unmarshaller, declaredType, reader)
        }

    @JvmStatic
    @Throws(JAXBException::class, SAXException::class, ParserConfigurationException::class)
    private fun <T> unmarshallFromReader(
            saxParserFactory: SAXParserFactory,
            unmarshaller: Unmarshaller,
            declaredType: Class<T>,
            reader: Reader): JAXBElement<T> =
        unmarshaller.unmarshal(SAXSource(saxParserFactory.newSAXParser().xmlReader, InputSource(reader)), declaredType)

    @JvmStatic
    @Throws(IOException::class)
    fun skipToXmlStart(baseInputStream: InputStream?): InputStream {
        val inputStream = PushbackInputStream(baseInputStream)
        while (true) {
            val i = inputStream.read()
            if (i == XML_START_CHAR) {
                inputStream.unread(XML_START_CHAR)
                break
            }
            if (i == -1) {
                break
            }
        }
        return inputStream
    }
}
