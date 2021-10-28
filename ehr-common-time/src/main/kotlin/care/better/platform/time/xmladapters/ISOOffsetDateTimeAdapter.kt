package care.better.platform.time.xmladapters

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Bostjan Lah
 */
class ISOOffsetDateTimeAdapter : XmlAdapter<String?, OffsetDateTime?>() {
    override fun unmarshal(v: String?): OffsetDateTime? {
        return if (v == null) null else OffsetDateTime.parse(v, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    override fun marshal(v: OffsetDateTime?): String? {
        return if (v == null) null else DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(v)
    }
}