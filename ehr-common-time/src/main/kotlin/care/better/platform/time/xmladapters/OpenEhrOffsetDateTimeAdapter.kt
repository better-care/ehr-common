package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrOffsetDateTime
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class OpenEhrOffsetDateTimeAdapter : XmlAdapter<String?, OpenEhrOffsetDateTime?>() {

    override fun unmarshal(v: String?): OpenEhrOffsetDateTime? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalOffsetDateTime(false)
                .parseDateTime(value) { temporal, precisionField, context -> OpenEhrOffsetDateTime.from(temporal, precisionField, context) }
        }
    }

    override fun marshal(v: OpenEhrOffsetDateTime?): String? {
        return v?.let { offsetDateTime -> OpenEhrDateTimeFormatter.ofOptionalOffsetDateTime().format(offsetDateTime) }
    }
}