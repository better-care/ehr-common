package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.jupiter.api.Test

/**
 * @author Bostjan Lah
 */
class ISODateTimeAdapterTest {

    @Test
    fun testMarshall() {
        val adapter = ISODateTimeAdapter()
        assertThat(adapter.marshal(DateTime(2000, 1, 1, 0, 0, DateTimeZone.UTC))).isEqualTo("2000-01-01T00:00:00.000Z")
        assertThat(adapter.marshal(null)).isNull()
    }

    @Test
    fun testUnmarshall() {
        val adapter = ISODateTimeAdapter()
        assertThat(adapter.unmarshal("2000-01-01T00:00:00.000Z")!!.withZone(DateTimeZone.UTC)).isEqualTo(DateTime(2000, 1, 1, 0, 0, DateTimeZone.UTC))
        assertThat(adapter.unmarshal(null)).isNull()
    }
}