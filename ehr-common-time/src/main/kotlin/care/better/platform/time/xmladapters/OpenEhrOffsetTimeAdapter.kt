package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrOffsetTime
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class OpenEhrOffsetTimeAdapter : XmlAdapter<String?, OpenEhrOffsetTime?>() {

    override fun unmarshal(v: String?): OpenEhrOffsetTime? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalOffsetTime(false)
                .parseTime(value) { temporal, precisionField, context -> OpenEhrOffsetTime.from(temporal, precisionField, context) }
        }
    }

    override fun marshal(v: OpenEhrOffsetTime?): String? {
        return v?.let { offsetTime -> OpenEhrDateTimeFormatter.ofOptionalOffsetTime().format(offsetTime) }
    }
}