package care.better.platform.time.xmladapters

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrLocalDate
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDateAdapter : XmlAdapter<String?, OpenEhrLocalDate?>() {

    override fun unmarshal(v: String?): OpenEhrLocalDate? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalLocalDate(false)
                .parseDate(value) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        }
    }

    override fun marshal(v: OpenEhrLocalDate?): String? {
        return v?.let { localDate -> OpenEhrDateTimeFormatter.ofOptionalLocalDate().format(localDate) }
    }
}