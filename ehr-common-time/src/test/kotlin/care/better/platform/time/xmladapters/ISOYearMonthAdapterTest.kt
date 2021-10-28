package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.YearMonth

/**
 * @author Matic Ribic
 */
class ISOYearMonthAdapterTest : TimeAdapterTestBase<ISOYearMonthAdapter>() {

    @BeforeEach
    fun setUp() {
        adapter = ISOYearMonthAdapter()
    }

    @Test
    fun marshallValidInput() {
        val result = adapter.marshal(YearMonth.of(2021, 8))
        assertThat(result).isEqualTo("2021-08")
    }

    @Test
    fun unmarshallValidInput() {
        val result = adapter.unmarshal("2021-08")
        assertThat(result).isEqualTo(YearMonth.of(2021, 8))
    }
}