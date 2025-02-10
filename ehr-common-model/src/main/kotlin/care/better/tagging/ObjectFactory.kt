package care.better.tagging

import care.better.tagging.dto.TagList
import care.better.tagging.dto.TagWithValueDto
import jakarta.xml.bind.annotation.XmlRegistry

/**
 * @author Domen Muren
 */
@XmlRegistry
class ObjectFactory {
    fun createTagWithValueDto(): TagWithValueDto {
        return TagWithValueDto()
    }

    fun createTagList(): TagList {
        return TagList()
    }
}
