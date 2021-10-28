package care.better.platform.time.xmladapters

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Bostjan Lah
 */
class ISOZonedDateTimeAdapter : XmlAdapter<String?, ZonedDateTime?>() {
    @Throws(Exception::class)
    override fun unmarshal(v: String?): ZonedDateTime? {
        return if (v == null) null else ZonedDateTime.parse(v, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    @Throws(Exception::class)
    override fun marshal(v: ZonedDateTime?): String? {
        return if (v == null) null else DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(v)
    }
}