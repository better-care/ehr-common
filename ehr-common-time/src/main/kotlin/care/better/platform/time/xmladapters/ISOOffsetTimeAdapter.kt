package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrField
import care.better.platform.time.temporal.OpenEhrOffsetTime
import java.time.OffsetTime
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class ISOOffsetTimeAdapter : XmlAdapter<String?, OffsetTime?>() {
    override fun unmarshal(v: String?): OffsetTime? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalOffsetTime(false).parseTime(value) { it, _, _ ->
                if (it is OpenEhrOffsetTime && it.precisionField <= OpenEhrField.SECONDS) {
                    OffsetTime.of(it.time.toLocalTime(), it.time.offset)
                } else {
                    OffsetTime.from(it)
                }
            }
        }
    }

    override fun marshal(v: OffsetTime?): String? {
        return v?.let { offsetTime -> OpenEhrDateTimeFormatter.ofOptionalOffsetTime().format(offsetTime) }
    }
}