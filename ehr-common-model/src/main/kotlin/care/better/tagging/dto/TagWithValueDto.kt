package care.better.tagging.dto

import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import java.util.*

/**
 * An extension of TagDto containing also a value (TAG:VALUE)
 *
 * @author Bostjan Lah
 * @since 2.3
 */
@XmlRootElement(name = "tag_with_value")
open class TagWithValueDto @JvmOverloads constructor(
    tag: String,
    @get:XmlElement
    val value: String? = null,
    aqlPath: String? = null
) : TagDto(tag, aqlPath) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is TagWithValueDto) {
            return false
        }
        if (!super.equals(other)) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), value)
    }

    override fun toString(): String {
        return super.toString() + "->" + value
    }

    companion object {
        private const val serialVersionUID = 2L
    }
}
