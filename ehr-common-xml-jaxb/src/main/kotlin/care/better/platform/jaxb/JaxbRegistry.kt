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

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller

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

        @JvmStatic
        @Throws(JAXBException::class)
        fun getInstance(): JaxbRegistry = INSTANCE

        @JvmStatic
        @Throws(JAXBException::class)
        fun createInstance(packages: List<String>) = JaxbRegistry(packages.filter { !CONTEXT_PATH.contains(it) })
    }

    private val context: JAXBContext = JAXBContext.newInstance(if (packages.isNotEmpty()) "$CONTEXT_PATH:${packages.joinToString(":")}" else CONTEXT_PATH)

    val marshaller: Marshaller = createMarshaller()

    val unmarshaller: Unmarshaller = createUnmarshaller()

    fun createMarshaller(): Marshaller = context.createMarshaller().apply { schema = null }

    fun createUnmarshaller(): Unmarshaller = context.createUnmarshaller().apply { schema = null }
}
