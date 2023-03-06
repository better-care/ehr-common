package care.better.tagging.dto

import java.io.Serializable
import javax.xml.bind.annotation.*

/**
 * @author Domen Muren
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAG_LIST")
@XmlRootElement
class TagList : Serializable {
    @XmlElement(name = "tag_with_value")
    var tags: List<TagWithValueDto>? = null

    constructor() {}
    constructor(tags: Collection<TagWithValueDto>?) {
        this.tags = ArrayList(tags)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
