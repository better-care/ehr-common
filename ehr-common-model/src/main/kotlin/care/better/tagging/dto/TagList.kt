package care.better.tagging.dto

import jakarta.xml.bind.annotation.*

/**
 * @author Domen Muren
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAG_LIST")
@XmlRootElement
open class TagList @JvmOverloads constructor(
    tags: Collection<TagWithValueDto> = listOf(),
) {
    @field:XmlElement(name = "tag_with_value")
    open val tags: MutableList<TagWithValueDto> = tags.toMutableList()

    companion object {
        private const val serialVersionUID = 2L
    }
}
