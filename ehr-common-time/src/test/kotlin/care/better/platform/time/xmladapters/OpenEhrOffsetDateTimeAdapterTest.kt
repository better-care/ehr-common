package care.better.platform.time.xmladapters

import care.better.platform.time.temporal.OpenEhrOffsetDateTime
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZoneOffset

/**
 * @author Matic Ribic
 */
class OpenEhrOffsetDateTimeAdapterTest : TimeAdapterTestBase<OpenEhrOffsetDateTimeAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = OpenEhrOffsetDateTimeAdapter()
    }

    @Test
    fun marshallInput() {
        val result = adapter.marshal(OpenEhrOffsetDateTime.of(2021, 8, 6, 4, 3, 2, null, ZoneOffset.ofHours(2)))
        Assertions.assertThat(result).isEqualTo("2021-08-06T04:03:02+02:00")
    }

    @Test
    fun unmarshallInput() {
        val result = adapter.unmarshal("2021-08-06T04:03:02+02:00")
        Assertions.assertThat(result).isEqualTo(OpenEhrOffsetDateTime.of(2021, 8, 6, 4, 3, 2, null, ZoneOffset.ofHours(2)))
    }
}