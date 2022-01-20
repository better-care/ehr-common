package care.better.tagging.dto

import java.io.Serializable
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * An object containing a string tag and optional aqlPath that is tagged with the string tag.
 *
 * @author matijak
 * @since 17.04.2014
 */
@XmlRootElement(name = "tag")
open class TagDto : Serializable {
    /**
     * Returns the string tag.
     *
     * @return tag
     */
    /**
     * Sets the string tag.
     *
     * @param tag tag
     */
    @get:XmlElement
    var tag: String? = null

    private var aqlPath: String? = null

    /**
     * Creates a new tag dto.
     */
    constructor() {}
    /**
     * Creates a new tag dto.
     * @param tag The string tag.
     * @param aqlPath The aql path. Can be null.
     */
    /**
     * Creates a new tag dto.
     * @param tag The string tag.
     */
    @JvmOverloads
    constructor(tag: String?, aqlPath: String? = "/") {
        this.tag = tag
        this.aqlPath = aqlPath
    }

    /**
     * Returns the aql path of this tag, if applicable.
     *
     * @return The aql path of the tag, or null if none.
     */
    @XmlElement
    fun getAqlPath(): String? {
        return aqlPath
    }

    /**
     * Sets the aql path.
     *
     * @param aqlPath Sets the aql path. Can be null.
     */
    fun setAqlPath(aqlPath: String? = "/") {
        this.aqlPath = aqlPath
    }

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
        result = 31 * result + if (aqlPath != null) aqlPath.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "$tag:$aqlPath"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
