package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import java.time.YearMonth
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class ISOYearMonthAdapter : XmlAdapter<String?, YearMonth?>() {

    override fun unmarshal(v: String?): YearMonth? {
        return v?.let { value -> OpenEhrDateTimeFormatter.ofOptionalLocalDate(false).parseDate(value) { it, _, _ -> YearMonth.from(it) } }
    }

    override fun marshal(v: YearMonth?): String? {
        return v?.let { yearMonth -> OpenEhrDateTimeFormatter.ofOptionalLocalDate().format(yearMonth) }
    }
}