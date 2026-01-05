package care.better.platform.jaxb

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.Reader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Source
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * @author Marko Pipan
 * @since 4.3.0
 *
 * Converts XML documents between different namespaces.
 */
class NamespaceTransformer(val fromNamespace: String, val toNamespace: String) {
    val namespaceUnawareFactory = DocumentBuilderFactory.newInstance().apply { isNamespaceAware = false }!!
    private val transformerFactory = TransformerFactory.newInstance()

    fun transform(inputStream: InputStream): InputStream {
        val document = namespaceUnawareFactory.newDocumentBuilder().parse(inputStream)

        transformNamespaces(document.documentElement)

        val transformer = transformerFactory.newTransformer()
        val output = ByteArrayOutputStream()
        transformer.transform(DOMSource(document), StreamResult(output))
        return output.toByteArray().inputStream()
    }

    fun transform(source: Source): Source = when (source) {
        is StreamSource -> when {
            source.reader != null -> StreamSource(transform(source.reader))
            else -> StreamSource(transform(source.inputStream))
        }
        is DOMSource -> DOMSource(transform(source.node))
        else -> throw UnsupportedOperationException("Transforming namespaces for source of type ${source.javaClass.name} is not supported")
    }

    fun transform(reader: Reader): Reader {
        val document = namespaceUnawareFactory.newDocumentBuilder().parse(InputSource(reader))
        transformNamespaces(document.documentElement)

        val transformer = transformerFactory.newTransformer()
        val writer = StringWriter()
        transformer.transform(DOMSource(document), StreamResult(writer))
        return writer.toString().reader()
    }

    fun transform(node: Node): Node {
        if (node.nodeType == Node.ELEMENT_NODE) {
            transformNamespaces(node as Element)
        } else if (node.nodeType == Node.DOCUMENT_NODE) {
            val doc = node as Document
            transformNamespaces(doc.documentElement)
        }
        return node
    }


    private fun transformNamespaces(element: Element) {
        val attributes = element.attributes
        for (i in 0 until attributes.length) {
            val attr = attributes.item(i)
            if (attr.nodeValue == fromNamespace) {
                attr.nodeValue = toNamespace
            }
        }

        val children = element.childNodes
        for (i in 0 until children.length) {
            val child = children.item(i)
            if (child.nodeType == Node.ELEMENT_NODE) {
                transformNamespaces(child as Element)
            }
        }
    }
}