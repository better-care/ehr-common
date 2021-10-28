package care.better.platform.time.xmladapters

import java.time.Duration
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matija Polajnar
 */
class ISODurationAdapter : XmlAdapter<String?, Duration?>() {
    override fun unmarshal(v: String?): Duration? {
        return if (v == null) null else Duration.parse(v)
    }

    override fun marshal(v: Duration?): String? {
        return v?.toString()
    }
}