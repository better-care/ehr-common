package care.better.platform.time.xmladapters

import care.better.platform.time.temporal.OpenEhrLocalDateTime
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDateTimeAdapterTest : TimeAdapterTestBase<OpenEhrLocalDateTimeAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = OpenEhrLocalDateTimeAdapter()
    }

    @Test
    fun marshallFullInput() {
        val result = adapter.marshal(OpenEhrLocalDateTime.of(2021, 8, 6, 4, 3, 2))
        Assertions.assertThat(result).isEqualTo("2021-08-06T04:03:02+02:00")
    }

    @Test
    fun marshallPartialInput() {
        val result = adapter.marshal(OpenEhrLocalDateTime.of(2021, 8, 6, 4))
        Assertions.assertThat(result).isEqualTo("2021-08-06T04")
    }

    @Test
    fun unmarshallFullInput() {
        val result = adapter.unmarshal("2021-08-06T04:03:02")
        Assertions.assertThat(result).isEqualTo(OpenEhrLocalDateTime.of(2021, 8, 6, 4, 3, 2))
    }

    @Test
    fun unmarshallFullInputWithOffset() {
        val result = adapter.unmarshal("2021-08-06T04:03:02+02:00")
        Assertions.assertThat(result).isEqualTo(OpenEhrLocalDateTime.of(2021, 8, 6, 4, 3, 2))
    }

    @Test
    fun unmarshallPartialInput() {
        val result = adapter.unmarshal("2021-08-06T04")
        Assertions.assertThat(result).isEqualTo(OpenEhrLocalDateTime.of(2021, 8, 6, 4))
    }
}