import care.better.platform.moxy.MoxyRegistry
import org.openehr.rm.composition.Composition
import javax.xml.bind.Marshaller
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 */


fun main() {
    //val compositionSource: StreamSource = StreamSource(XmlTest::class.java.classLoader.getResourceAsStream("careplan_composition.xml"))


    val composition: Composition = MoxyRegistry.getInstance().unmarshaller.unmarshal(
            StreamSource(XmlTest::class.java.classLoader.getResourceAsStream("careplan_composition.xml")),
            Composition::class.java).value

    MoxyRegistry.getInstance().marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    MoxyRegistry.getInstance().marshaller.marshal(composition, System.out)
}

class XmlTest  {

}