package care.better.platform.time.xmladapters

import care.better.platform.time.temporal.OpenEhrOffsetTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZoneOffset

/**
 * @author Matic Ribic
 */
class OpenEhrOffsetTimeAdapterTest : TimeAdapterTestBase<OpenEhrOffsetTimeAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = OpenEhrOffsetTimeAdapter()
    }

    @Test
    fun marshallValidInput() {
        val result = adapter.marshal(OpenEhrOffsetTime.of(4, 3, 2, null, ZoneOffset.ofHours(1)))
        assertThat(result).isEqualTo("04:03:02+01:00")
    }

    @Test
    fun unmarshallValidInput() {
        val result = adapter.unmarshal("04:03:02+01:00")
        assertThat(result).isEqualTo(OpenEhrOffsetTime.of(4, 3, 2, null, ZoneOffset.ofHours(1)))
    }
}