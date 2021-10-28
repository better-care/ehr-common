package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import java.time.Year
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class ISOYearAdapter : XmlAdapter<String?, Year?>() {

    override fun unmarshal(v: String?): Year? {
        return v?.let { value -> OpenEhrDateTimeFormatter.ofOptionalLocalDate(false).parseDate(value) { it, _, _ -> Year.from(it) } }
    }

    override fun marshal(v: Year?): String? {
        return v?.let { year -> OpenEhrDateTimeFormatter.ofOptionalLocalDate().format(year) }
    }
}