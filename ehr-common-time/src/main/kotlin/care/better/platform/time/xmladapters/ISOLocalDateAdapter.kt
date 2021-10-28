package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import java.time.LocalDate
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class ISOLocalDateAdapter : XmlAdapter<String?, LocalDate?>() {

    override fun unmarshal(v: String?): LocalDate? {
        return v?.let { value -> OpenEhrDateTimeFormatter.ofLocalDate(false).parseDate(value) { it, _, _ -> LocalDate.from(it) } }
    }

    override fun marshal(v: LocalDate?): String? {
        return v?.let { localDate -> OpenEhrDateTimeFormatter.ofLocalDate().format(localDate) }
    }
}