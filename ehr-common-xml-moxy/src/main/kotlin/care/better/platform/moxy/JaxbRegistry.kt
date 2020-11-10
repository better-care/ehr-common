package care.better.platform.moxy

import java.security.AccessController
import java.security.PrivilegedAction
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 */

class JaxbRegistry {

    companion object {
        private val INSTANCE: JaxbRegistry = JaxbRegistry()

        fun getInstance(): JaxbRegistry = INSTANCE
    }

    private val context: JAXBContext = JAXBContext.newInstance(
            "org.openehr.am.aom:" +
                    "org.openehr.base.basetype:" +
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
                this["org.openehr.am.aom"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("aom-oxm.xml"))
                this["org.openehr.base.basetype"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("basetype-oxm.xml"))
                this["org.openehr.base.foundationtypes"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("foundationtypes-oxm.xml"))
                this["org.openehr.base.resource"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("resource-oxm.xml"))
                this["org.openehr.proc.taskplanning"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("taskplanning-oxm.xml"))
                this["org.openehr.rm.common"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("common-oxm.xml"))
                this["org.openehr.rm.composition"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("composition-oxm.xml"))
                this["org.openehr.rm.datastructures"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("datastructures-oxm.xml"))
                this["org.openehr.rm.datatypes"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("datatypes-oxm.xml"))
                this["org.openehr.rm.ehr"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("ehr-oxm.xml"))
                this["org.openehr.rm.integration"] = StreamSource(JaxbRegistry::class.java.getResourceAsStream("integration-oxm.xml"))
                this.toMap()
            })

    val marshaller: Marshaller = context.createMarshaller()
    val unmarshaller: Unmarshaller = context.createUnmarshaller()
}