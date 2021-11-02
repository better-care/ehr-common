package care.better.platform.time.xmladapters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.temporal.Temporal
import java.util.*
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
abstract class TimeAdapterTestBase<U : XmlAdapter<String?, out Temporal?>> {
    protected lateinit var adapter: U

    companion object {
        private lateinit var defaultTimeZone: TimeZone

        @BeforeAll
        @JvmStatic
        @Suppress("unused")
        internal fun init() {
            defaultTimeZone = TimeZone.getDefault()
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/Ljubljana"))
        }

        @AfterAll
        @JvmStatic
        @Suppress("unused")
        internal fun tearDown() {
            TimeZone.setDefault(defaultTimeZone)
        }
    }

    @Test
    fun marshallNull() {
        assertThat(adapter.marshal(null)).isNull()
    }

    @Test
    fun unmarshallNull() {
        assertThat(adapter.unmarshal(null)).isNull()
    }
}