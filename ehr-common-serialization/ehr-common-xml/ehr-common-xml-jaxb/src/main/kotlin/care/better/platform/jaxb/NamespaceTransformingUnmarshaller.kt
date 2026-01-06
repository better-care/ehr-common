package care.better.platform.jaxb

import jakarta.xml.bind.JAXBElement
import jakarta.xml.bind.Unmarshaller
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.net.URL
import javax.xml.transform.Source

/**
 * @author Marko Pipan
 * @since 4.3.0
 *
 * A decorator that transforms XML namespaces when unmarshalling xml into objects. Beware that not all input types are supported.
 * Some input types (Source, XMLStreamReader, and XMLEventReader, JAXBSource) will not transform the namespace.
 */
class NamespaceTransformingUnmarshaller(
    fromNamespace: String, toNamespace: String, private val delegate: Unmarshaller) : Unmarshaller by delegate {

    private val transformer = NamespaceTransformer(fromNamespace, toNamespace)

    override fun unmarshal(file: File): Any {
        return file.inputStream().use { delegate.unmarshal(transformer.transform(it)) }
    }

    override fun unmarshal(inputStream: InputStream): Any {
        return delegate.unmarshal(transformer.transform(inputStream))
    }


    override fun unmarshal(reader: Reader): Any {
        return delegate.unmarshal(transformer.transform(reader))
    }

    override fun unmarshal(url: URL): Any {
        return transformer.transform(url.openStream()).use { delegate.unmarshal(it) }
    }

    override fun unmarshal(inputSource: InputSource): Any {
        val transformed = try {
            transformer.transform(inputSource)
        } catch (_: UnsupportedOperationException) {
            return delegate.unmarshal(inputSource)
        }
        return delegate.unmarshal(transformed)
    }

    override fun unmarshal(node: Node): Any {
        return delegate.unmarshal(transformer.transform(node))
    }

    override fun <T : Any> unmarshal(node: Node, type: Class<T>): JAXBElement<T> {
        transformer.transform(node)
        return delegate.unmarshal(node, type)
    }

    override fun unmarshal(source: Source): Any {
        val transformed = try {
            transformer.transform(source)
        } catch (_: UnsupportedOperationException) {
            return delegate.unmarshal(source)
        }
        return delegate.unmarshal(transformed)
    }

    override fun <T : Any> unmarshal(source: Source, type: Class<T>): JAXBElement<T> {
        val transformed = try {
            transformer.transform(source)
        } catch (_: UnsupportedOperationException) {
            return delegate.unmarshal(source, type)
        }
        return delegate.unmarshal(transformed, type)
    }

}
