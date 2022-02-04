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

package care.better.platform.template

import care.better.platform.jaxb.JaxbRegistry
import org.openehr.am.aom.Template
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PushbackInputStream
import java.nio.charset.StandardCharsets
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
abstract class AbstractAmTest {

    companion object {
        private const val XML_START_CHAR = '<'.code
    }

    private val jaxbRegistry =
        try {
            JaxbRegistry.getInstance()
        } catch (e: JAXBException) {
            throw IllegalStateException("Error creating JAXB context", e)
        }

    @Throws(IOException::class)
    protected open fun loadTemplate(resourceName: String): Template = parse(AbstractAmTest::class.java.getResourceAsStream(resourceName))

    @Throws(JAXBException::class)
    private fun getUnmarshaller(): Unmarshaller =
        jaxbRegistry.createUnmarshaller().apply {
            this.schema = null
        }

    @Throws(IOException::class)
    private fun parse(inputStream: InputStream): Template {
        try {
            InputStreamReader(skipToXmlStart(inputStream), StandardCharsets.UTF_8).use { reader ->
                return getUnmarshaller().unmarshal(StreamSource(reader), Template::class.java).value
            }
        } catch (e: JAXBException) {
            throw IllegalStateException("Error parsing template!", e)
        }
    }

    @Throws(IOException::class)
    private fun skipToXmlStart(baseInputStream: InputStream): InputStream {
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
