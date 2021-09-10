package care.better.platform.time.xmladapters

import care.better.platform.time.temporal.OpenEhrLocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDateAdapterTest : TimeAdapterTestBase<OpenEhrLocalDateAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = OpenEhrLocalDateAdapter()
    }

    @Test
    fun marshallFullInput() {
        val result = adapter.marshal(OpenEhrLocalDate.of(2021, 8, 6))
        assertThat(result).isEqualTo("2021-08-06")
    }

    @Test
    fun marshallPartialInput() {
        val result = adapter.marshal(OpenEhrLocalDate.of(2021, 8))
        assertThat(result).isEqualTo("2021-08")
    }

    @Test
    fun unmarshallFullInput() {
        val result = adapter.unmarshal("2021-08-06")
        assertThat(result).isEqualTo(OpenEhrLocalDate.of(2021, 8, 6))
    }

    @Test
    fun unmarshallPartialInput() {
        val result = adapter.unmarshal("2021-08")
        assertThat(result).isEqualTo(OpenEhrLocalDate.of(2021, 8))
    }
}