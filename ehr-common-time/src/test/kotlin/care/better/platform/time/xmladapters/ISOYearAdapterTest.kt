package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Year

/**
 * @author Matic Ribic
 */
class ISOYearAdapterTest : TimeAdapterTestBase<ISOYearAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = ISOYearAdapter()
    }

    @Test
    fun marshallValidInput() {
        val result = adapter.marshal(Year.of(2021))
        assertThat(result).isEqualTo("2021")
    }

    @Test
    fun unmarshallValidInput() {
        val result = adapter.unmarshal("2021")
        assertThat(result).isEqualTo(Year.of(2021))
    }
}