package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrLocalTime
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class OpenEhrLocalTimeAdapter : XmlAdapter<String?, OpenEhrLocalTime?>() {

    override fun unmarshal(v: String?): OpenEhrLocalTime? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalLocalTime(false)
                .parseTime(value) { temporal, precisionField, context -> OpenEhrLocalTime.from(temporal, precisionField, context) }
        }
    }

    override fun marshal(v: OpenEhrLocalTime?): String? {
        return v?.let { localTime -> OpenEhrDateTimeFormatter.ofOptionalLocalTime().format(localTime) }
    }
}