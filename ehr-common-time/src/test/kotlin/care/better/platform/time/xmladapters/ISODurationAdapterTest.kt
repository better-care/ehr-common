package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Duration

/**
 * @author Bostjan Lah
 */
class ISODurationAdapterTest {

    companion object {
        private val TEST_DURATION = Duration.ofHours(1L).plusMinutes(5L)
        private const val TEST_DURATION_REPR = "PT1H5M"
    }

    @Test
    fun testMarshall() {
        val adapter = ISODurationAdapter()
        assertThat(adapter.marshal(TEST_DURATION)).isEqualTo(TEST_DURATION_REPR)
        assertThat(adapter.marshal(null)).isNull()
    }

    @Test
    fun testUnmarshall() {
        val adapter = ISODurationAdapter()
        assertThat(adapter.unmarshal(TEST_DURATION_REPR)).isEqualTo(TEST_DURATION)
        assertThat(adapter.unmarshal(null)).isNull()
    }
}