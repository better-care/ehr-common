package care.better.platform.jaxb

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller

/**
 * @author Primoz Delopst
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
