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

import care.better.platform.jaxb.JaxbRegistry.Companion.OPENEHR_NAMESPACE_V1
import care.better.platform.jaxb.JaxbRegistry.Companion.OPENEHR_NAMESPACE_V2
import care.better.platform.utils.XmlUtils
import care.better.platform.utils.XmlUtils.unmarshal
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import javax.xml.bind.*
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class JaxbRegistry(packages: List<String>) {

    companion object {

        @JvmStatic
        private val INSTANCE: JaxbRegistry = JaxbRegistry(emptyList())

        private const val CONTEXT_PATH: String =
            "org.openehr.am.aom:" +
                    "org.openehr.base.basetypes:" +
                    "org.openehr.base.foundationtypes:" +
                    "org.openehr.base.resource:" +
                    "org.openehr.proc.taskplanning:" +
                    "org.openehr.rm.common:" +
                    "org.openehr.rm.composition:" +
                    "org.openehr.rm.datastructures:" +
                    "org.openehr.rm.datatypes:" +
                    "org.openehr.rm.ehr:" +
                    "org.openehr.rm.integration"

        const val OPENEHR_NAMESPACE_V1 = "http://schemas.openehr.org/v1"
        const val OPENEHR_NAMESPACE_V2 = "http://schemas.openehr.org/v2"

        /**
         * The target namespace to serialize the xml to. Be aware that when using any namespace other than
         * [OPENEHR_NAMESPACE_V1], not all marshaller output types will be supported. Namely: XMLEventWriter, XMLStreamWriter and ContentHandler.
         */
        var serializeWithNamespace = OPENEHR_NAMESPACE_V1

        /** Should the [OPENEHR_NAMESPACE_V2] be supported during unmarshalling. When this is set to true, you will be able to
         * deserialize templates with both V1 and V2 namespaces, but not all unmarshaller input types will be supported.
         * Namely: Source, XMLStreamReader, and XMLEventReader.
         * */
        var supportDeserializingV2Namespace = true

        @JvmStatic
        @Throws(JAXBException::class)
        fun getInstance(): JaxbRegistry = INSTANCE

        @JvmStatic
        @Throws(JAXBException::class)
        fun createInstance(packages: List<String>) = JaxbRegistry(packages.filter { !CONTEXT_PATH.contains(it) })
    }

    private val context: JAXBContext = JAXBContext.newInstance(if (packages.isNotEmpty()) "$CONTEXT_PATH:${packages.joinToString(":")}" else CONTEXT_PATH)
    private val saxParserFactory: SAXParserFactory = XmlUtils.createSAXParserFactory()

    val marshaller: Marshaller get() = createMarshaller()

    val unmarshaller: Unmarshaller get() = createUnmarshaller()

    fun createMarshaller(): Marshaller = context.createMarshaller().apply { schema = null }
        .let { original ->
            if (serializeWithNamespace == OPENEHR_NAMESPACE_V1) {
                original
            } else {
                NamespaceTransformingMarshaller(OPENEHR_NAMESPACE_V1, OPENEHR_NAMESPACE_V2, original)
            }
        }

    fun createUnmarshaller(): Unmarshaller = context.createUnmarshaller().apply { schema = null }
        .let { original ->
            if (supportDeserializingV2Namespace) {
                NamespaceTransformingUnmarshaller(OPENEHR_NAMESPACE_V2, OPENEHR_NAMESPACE_V1, original)
            } else {
                original
            }
        }

    @Throws(JAXBException::class, ParserConfigurationException::class, SAXException::class, IOException::class)
    fun <T> unmarshal(stringToUnmarshall: String, clazz: Class<T>): JAXBElement<T> {
        return unmarshal(saxParserFactory, createUnmarshaller(), stringToUnmarshall, clazz)
    }

    @Throws(JAXBException::class, ParserConfigurationException::class, SAXException::class, IOException::class)
    fun <T> unmarshal(inputStream: InputStream, clazz: Class<T>): JAXBElement<T> {
        return unmarshal(saxParserFactory, createUnmarshaller(), inputStream, clazz)
    }
}
