package care.better.platform.moxy

import org.eclipse.persistence.jaxb.xmlmodel.XmlBindings
import javax.xml.bind.JAXBContext
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 */

fun main() {
    MoxyBindings.getInstance().xmlBindings
}

class MoxyBindings {

    companion object {
        private val INSTANCE = MoxyBindings()

        @JvmStatic
        fun getInstance(): MoxyBindings = INSTANCE
    }

    val xmlBindings: XmlBindings = with(JAXBContext.newInstance("org.eclipse.persistence.jaxb.xmlmodel")) {
        val xmlBindings: XmlBindings = getXmlBindings(this, "/aom-oxm.xml")

        getXmlBindings(this, "/basetype-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/foundationtypes-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/resource-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/taskplanning-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/common-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/composition-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/datastructures-oxm.xml").also { addTypes(xmlBindings, it) }

        getXmlBindings(this, "/datatypes-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/ehr-oxm.xml").also { addTypes(xmlBindings, it) }
        getXmlBindings(this, "/integration-oxm.xml").also { addTypes(xmlBindings, it) }

        xmlBindings
    }


    private fun addTypes(x1: XmlBindings, x2: XmlBindings) {
        x1.javaTypes.javaType.addAll(x2.javaTypes.javaType)
        x1.xmlEnums.xmlEnum.addAll(x2.xmlEnums.xmlEnum)
    }

    private fun getXmlBindings(jaxbContext: JAXBContext, fileName: String): XmlBindings =
            jaxbContext.createUnmarshaller().unmarshal(
                    StreamSource(MoxyRegistry::class.java.getResourceAsStream(fileName)),
                    XmlBindings::class.java).value
}