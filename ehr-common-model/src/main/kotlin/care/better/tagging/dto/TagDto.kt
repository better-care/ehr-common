package care.better.tagging.dto

import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import java.io.Serializable

/**
 * An object containing a string tag and optional aqlPath that is tagged with the string tag.
 *
 * @author matijak
 * @since 17.04.2014
 */
@XmlRootElement(name = "tag")
open class TagDto @JvmOverloads constructor(
    @get:XmlElement
    open val tag: String,
    @get:XmlElement
    open val aqlPath: String? = "/"
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val tagDto = other as TagDto
        return if (if (aqlPath != null) aqlPath != tagDto.aqlPath else tagDto.aqlPath != null) {
            false
        } else tag == tagDto.tag
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + (aqlPath?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "$tag:$aqlPath"
    }

    companion object {
        private const val serialVersionUID = 2L
    }
}
