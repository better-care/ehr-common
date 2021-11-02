package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 * @author Matic Ribic
 */
class ISOLocalDateAdapterTest : TimeAdapterTestBase<ISOLocalDateAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = ISOLocalDateAdapter()
    }

    @Test
    fun marshallValidInput() {
        val result = adapter.marshal(LocalDate.of(2021, 8, 6))
        assertThat(result).isEqualTo("2021-08-06")
    }

    @Test
    fun unmarshallValidInput() {
        val result = adapter.unmarshal("2021-08-06")
        assertThat(result).isEqualTo(LocalDate.of(2021, 8, 6))
    }
}