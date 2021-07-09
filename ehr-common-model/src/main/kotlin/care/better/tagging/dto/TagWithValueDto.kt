package care.better.tagging.dto

import java.util.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * An extension of TagDto containing also a value (TAG:VALUE)
 *
 * @author Bostjan Lah
 * @since 2.3
 */
@XmlRootElement(name = "tag_with_value")
class TagWithValueDto : TagDto {
    @get:XmlElement
    var value: String? = null

    constructor() {}

    @JvmOverloads
    constructor(tag: String?, value: String? = null, aqlPath: String? = null) : super(tag, aqlPath) {
        this.value = value
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is TagWithValueDto) {
            return false
        }
        if (!super.equals(o)) {
            return false
        }
        return value == o.value
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), value)
    }

    override fun toString(): String {
        return super.toString() + "->" + value
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
