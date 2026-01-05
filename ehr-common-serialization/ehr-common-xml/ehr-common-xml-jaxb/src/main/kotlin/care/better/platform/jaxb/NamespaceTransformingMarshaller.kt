package care.better.platform.jaxb

import org.w3c.dom.Node
import org.xml.sax.ContentHandler
import java.io.*
import jakarta.xml.bind.Marshaller
import javax.xml.stream.XMLEventWriter
import javax.xml.stream.XMLStreamWriter
import javax.xml.transform.Result

/**
 * @author Marko Pipan
 * @since 4.3.0
 *
 * A decorator that transforms XML namespaces when marshalling objects. Beware that not all output types are supported.
 */
class NamespaceTransformingMarshaller(
    fromNamespace: String, toNamespace: String, private val delegate: Marshaller) : Marshaller by delegate {

    private val transformer = NamespaceTransformer(fromNamespace, toNamespace)

    override fun marshal(from: Any, target: Result) {
        throw UnsupportedOperationException("Transforming namespaces when marshalling to Result is not supported")
    }

    override fun marshal(from: Any, output: OutputStream) {
        val bout = ByteArrayOutputStream()
        delegate.marshal(from, bout)
        val transformed = transformer.transform(bout.toByteArray().inputStream())
        transformed.copyTo(output)
   }

    override fun marshal(from: Any, to: File) {
        val bout = ByteArrayOutputStream()
        delegate.marshal(from, bout)
        val transformed = transformer.transform(bout.toByteArray().inputStream())
        to.outputStream().use { transformed.copyTo(it) }
    }

    override fun marshal(from: Any, writer: Writer) {
        val stringWriter = StringWriter()
        delegate.marshal(from, stringWriter)
        val transformed = transformer.transform(stringWriter.toString().reader())
        transformed.copyTo(writer)
    }

    override fun marshal(from: Any, to: ContentHandler) {
        throw UnsupportedOperationException("Transforming namespaces when marshalling to ContentHandler is not supported")
    }

    override fun marshal(from: Any, to: Node) {
        transformer.transform(to)
        delegate.marshal(from, to)
    }

    override fun marshal(from: Any, to: XMLStreamWriter) {
        throw UnsupportedOperationException("Transforming namespaces when marshalling to XMLStreamWriter is not supported")
    }

    override fun marshal(from: Any, to: XMLEventWriter) {
        throw UnsupportedOperationException("Transforming namespaces when marshalling to XMLEventWriter is not supported")
    }
}
