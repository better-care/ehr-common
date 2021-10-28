package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetTime
import java.time.ZoneOffset

/**
 * @author Matic Ribic
 */
class ISOOffsetTimeAdapterTest : TimeAdapterTestBase<ISOOffsetTimeAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = ISOOffsetTimeAdapter()
    }

    @Test
    fun marshallValidInput() {
        val result = adapter.marshal(OffsetTime.of(4, 3, 2, 0, ZoneOffset.ofHours(1)))
        assertThat(result).isEqualTo("04:03:02.000+01:00")
    }

    @Test
    fun unmarshallInputWithMillis() {
        val result = adapter.unmarshal("04:03:02.789+01:00")
        assertThat(result).isEqualTo(OffsetTime.of(4, 3, 2, 789000000, ZoneOffset.ofHours(1)))
    }

    @Test
    fun unmarshallInputWithoutMillis() {
        val result = adapter.unmarshal("04:03:02+01:00")
        assertThat(result).isEqualTo(OffsetTime.of(4, 3, 2, 0, ZoneOffset.ofHours(1)))
    }
}