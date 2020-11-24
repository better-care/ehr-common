/* Copyright 2020-2025 Better Ltd (www.better.care)
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

package care.better.platform.moxy

import org.eclipse.persistence.jaxb.JAXBContextFactory
import java.security.AccessController
import java.security.PrivilegedAction
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 */

class MoxyRegistry {

    companion object {
        private val INSTANCE: MoxyRegistry = MoxyRegistry()

        @JvmStatic
        fun getInstance(): MoxyRegistry = INSTANCE
    }

    private val context: JAXBContext = JAXBContext.newInstance(
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
                    "org.openehr.rm.integration",
            if (System.getSecurityManager() == null)
                Thread.currentThread().contextClassLoader
            else
                AccessController.doPrivileged(PrivilegedAction { Thread.currentThread().contextClassLoader }) as ClassLoader,
            with(mutableMapOf<String, Any?>()) {
                this["org.openehr.am.aom"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/aom-oxm.xml"))
                this["org.openehr.base.basetypes"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/basetype-oxm.xml"))
                this["org.openehr.base.foundationtypes"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/foundationtypes-oxm.xml"))
                this["org.openehr.base.resource"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/resource-oxm.xml"))
                this["org.openehr.proc.taskplanning"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/taskplanning-oxm.xml"))
                this["org.openehr.rm.common"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/common-oxm.xml"))
                this["org.openehr.rm.composition"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/composition-oxm.xml"))
                this["org.openehr.rm.datastructures"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/datastructures-oxm.xml"))
                this["org.openehr.rm.datatypes"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/datatypes-oxm.xml"))
                this["org.openehr.rm.ehr"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/ehr-oxm.xml"))
                this["org.openehr.rm.integration"] = StreamSource(MoxyRegistry::class.java.getResourceAsStream("/integration-oxm.xml"))
                mapOf<String, Any>(Pair(JAXBContextFactory.ECLIPSELINK_OXM_XML_KEY, this.toMap()))
            })

    val marshaller: Marshaller = context.createMarshaller().apply { schema = null }
    val unmarshaller: Unmarshaller = context.createUnmarshaller().apply { schema = null }
}