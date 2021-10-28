package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrLocalDateTime
import care.better.platform.time.temporal.OpenEhrOffsetDateTime
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDateTimeAdapter : XmlAdapter<String?, OpenEhrLocalDateTime?>() {

    override fun unmarshal(v: String?): OpenEhrLocalDateTime? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalLocalDateTime(false)
                .parseDateTime(value) { temporal, precisionField, context ->
                    if (temporal is OpenEhrOffsetDateTime) {
                        temporal.toLocalDateTime()
                    } else {
                        OpenEhrLocalDateTime.from(temporal, precisionField, context)
                    }
                }
        }
    }

    override fun marshal(v: OpenEhrLocalDateTime?): String? {
        return v?.let { localDateTime -> OpenEhrDateTimeFormatter.ofOptionalLocalDateTime().format(localDateTime) }
    }
}