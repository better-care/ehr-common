package care.better.platform.time.xmladapters

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Bostjan Lah
 */
class ISODateTimeAdapter : XmlAdapter<String?, DateTime?>() {
    override fun unmarshal(v: String?): DateTime? {
        return if (v == null) null else DATE_TIME_PARSER.parseDateTime(v)
    }

    override fun marshal(v: DateTime?): String? {
        return if (v == null) null else DATE_TIME_FORMATTER.print(v)
    }

    companion object {
        private val DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime().withOffsetParsed()
        private val DATE_TIME_PARSER = ISODateTimeFormat.dateTimeParser().withOffsetParsed()
    }
}