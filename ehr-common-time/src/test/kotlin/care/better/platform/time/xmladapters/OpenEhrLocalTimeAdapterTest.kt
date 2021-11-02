package care.better.platform.time.xmladapters

import care.better.platform.time.temporal.OpenEhrLocalTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Matic Ribic
 */
class OpenEhrLocalTimeAdapterTest : TimeAdapterTestBase<OpenEhrLocalTimeAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = OpenEhrLocalTimeAdapter()
    }

    @Test
    fun marshallFullInput() {
        val result = adapter.marshal(OpenEhrLocalTime.of(4, 3, 2))
        assertThat(result).isEqualTo("04:03:02")
    }

    @Test
    fun marshallPartialInput() {
        val result = adapter.marshal(OpenEhrLocalTime.of(4))
        assertThat(result).isEqualTo("04")
    }

    @Test
    fun unmarshallFullInput() {
        val result = adapter.unmarshal("04:03:02")
        assertThat(result).isEqualTo(OpenEhrLocalTime.of(4, 3, 2))
    }

    @Test
    fun unmarshallPartialInput() {
        val result = adapter.unmarshal("04")
        assertThat(result).isEqualTo(OpenEhrLocalTime.of(4))
    }
}