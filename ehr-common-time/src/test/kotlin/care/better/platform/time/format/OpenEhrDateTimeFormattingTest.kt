package care.better.platform.time.format

import care.better.platform.time.temporal.OpenEhrLocalDate
import care.better.platform.time.temporal.OpenEhrLocalDateTime
import care.better.platform.time.temporal.OpenEhrOffsetDateTime
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.*
import java.time.temporal.ChronoField
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.util.*
import java.util.stream.Stream

/**
 * @author Matic Ribic
 */
class OpenEhrDateTimeFormattingTest {

    companion object {
        private lateinit var defaultTimeZone: TimeZone
        private const val CONVERSION_EXCEPTION = "CONVERSION_EXCEPTION"

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

        @JvmStatic
        @Suppress("unused")
        fun providePatternDateTimeAndExpectedResult(): Stream<Arguments> = Stream.of(
                // UTC datetime pattern, datetime in timezone
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35-0400", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35-04", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35.654789-04:00", "2021-08-06T23:17:35-04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17:35+0400", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17:35+04", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35Z", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2013-1-1T01:00:17.000Z", "2013-01-01T01:00:17Z", CONVERSION_EXCEPTION),

                // UTC datetime pattern, local datetime
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17:35", "2021-08-06T01:17:35+02:00", CONVERSION_EXCEPTION),

                // UTC datetime pattern, partial datetime in timezone
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17-04:00", "2021-08-06T23:17:00-04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17+04:00", "2021-08-06T01:17:00+04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01+04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17Z", "2021-08-06T23:17:00Z", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // UTC datetime pattern, local partial datetime
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01:17", "2021-08-06T01:17:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T01", "2021-08-06T01:00:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06", "2021-08-06T00:00:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08", "2021-08-01T00:00:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SSZ", "2021", "2021-01-01T00:00:00+01:00", CONVERSION_EXCEPTION),

                // local datetime pattern, datetime in timezone
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35Z", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17:35.000+04:00", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35+04:00"),

                // local datetime pattern, local datetime
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01:17:35", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17:35.654", "2021-08-06T23:17:35+02:00", CONVERSION_EXCEPTION),

                // local datetime pattern, partial datetime in timezone
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17-04:00", "2021-08-06T23:17:00-04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01:17+04:00", "2021-08-06T01:17:00+04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01+04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17Z", "2021-08-06T23:17:00Z", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // local datetime pattern, local partial datetime
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01:17", "2021-08-06T01:17:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01", "2021-08-06T01:00:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06", "2021-08-06T00:00:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08", "2021-08-01T00:00:00+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS", "2021", "2021-01-01T00:00:00+01:00", CONVERSION_EXCEPTION),

                // optional seconds pattern, datetime in timezone
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00", "2021-08-06T01:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35Z", CONVERSION_EXCEPTION),

                // optional seconds pattern, local datetime
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17:35", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),

                // optional seconds pattern, partial datetime in timezone
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23:17-04:00", "2021-08-07T05:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17+04:00", "2021-08-05T23:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01+04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23:17Z", "2021-08-07T01:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // optional seconds pattern, local partial datetime
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01", "2021-08-06T01:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06", "2021-08-06T00:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021-08", "2021-08-01T00:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:??", "2021", "2021-01-01T00:00", CONVERSION_EXCEPTION),

                // not allowed seconds pattern, datetime in timezone
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23:17:35-04:00", "2021-08-07T05:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01:17:35+04:00", "2021-08-05T23:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23:17:35Z", "2021-08-07T01:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23:17:35.654Z", "2021-08-07T01:17", CONVERSION_EXCEPTION),

                // not allowed seconds pattern, local datetime
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01:17:35", "2021-08-06T01:17", CONVERSION_EXCEPTION),

                // not allowed seconds pattern, partial datetime in timezone
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23:17-04:00", "2021-08-07T05:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01:17+04:00", "2021-08-05T23:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01+04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23:17Z", "2021-08-07T01:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // not allowed seconds pattern, local partial datetime
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01", "2021-08-06T01:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06", "2021-08-06T00:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021-08", "2021-08-01T00:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:XX", "2021", "2021-01-01T00:00", CONVERSION_EXCEPTION),

                // optional hour and minute and not allowed seconds pattern, datetime in timezone
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T23:17:35-04:00", "2021-08-07T05:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01:17:35+04:00", "2021-08-05T23:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T23:17:35Z", "2021-08-07T01:17", CONVERSION_EXCEPTION),

                // optional hour and minute and not allowed seconds pattern, local datetime
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01:17:35", "2021-08-06T01:17", CONVERSION_EXCEPTION),

                // optional hour and minute and not allowed seconds pattern, partial datetime in timezone
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T23:17-04:00", "2021-08-07T05:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01:17+04:00", "2021-08-05T23:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01+04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T23:17Z", "2021-08-07T01:17", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // optional hour and minute and not allowed seconds pattern, local partial datetime
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01", "2021-08-06T01", "2021-08-06T01"),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T", "CONVERSION_EXCEPTION", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-mm-ddT??:??:XX", "2021-08", "2021-08-01", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddT??:??:XX", "2021", "2021-01-01", CONVERSION_EXCEPTION),

                // case insensitive pattern
                args("yyyy-mm-ddthh:mm:ss", "2021-08-06T01:17:35", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("YYYY-MM-DDTHH:MM:SS", "2021-08-06T01:17:35", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("yyyy-mm-ddthh:mm:xx", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),
                args("YYYY-MM-DDTHH:MM:XX", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),

                // not allowed pattern
                args("yyyy-XX-XXTXX:XX:XX", "2021-08-06T01:17:35", "2021", CONVERSION_EXCEPTION),
                args("yyyy-XX-XXTXX:XX:XX", "2021-08-06T01:17", "2021", CONVERSION_EXCEPTION),
                args("yyyy-XX-XXTXX:XX:XX", "2021-08-06T01", "2021", CONVERSION_EXCEPTION),
                args("yyyy-XX-XXTXX:XX:XX", "2021-08-06", "2021", CONVERSION_EXCEPTION),
                args("yyyy-XX-XXTXX:XX:XX", "2021-08", "2021", CONVERSION_EXCEPTION),
                args("yyyy-XX-XXTXX:XX:XX", "2021", "2021", "2021"),
                args("yyyy-MM-ddTXX:XX:XX", "2021-08-06T01", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-MM-ddTXX:XX:XX", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-MM-ddTHH:XX:XX", "2021-08-06T01", "2021-08-06T01", "2021-08-06T01"),

                // optional pattern
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17:35", "2021-08-06T01:17:35+02:00", "2021-08-06T01:17:35+02:00"),
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17:35.654789", "2021-08-06T01:17:35+02:00", CONVERSION_EXCEPTION),
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17:35,654789", "2021-08-06T01:17:35+02:00", CONVERSION_EXCEPTION),
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),
                args("yyyy-??-??T??:??:??", "2021-08-06T01", "2021-08-06T01", "2021-08-06T01"),
                args("yyyy-??-??T??:??:??", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-??-??T??:??:??", "2021-08", "2021-08", "2021-08"),
                args("yyyy-??-??T??:??:??", "2021", "2021", "2021"),
                args("yyyy-MM-??T??:??:??", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-MM-??T??:??:??", "2021-08", "2021-08", "2021-08"),
                args("yyyy-MM-ddT??:??:??", "2021-08-06T01", "2021-08-06T01", "2021-08-06T01"),
                args("yyyy-MM-ddT??:??:??", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-MM-ddTHH:??:??", "2021-08-06T01:17", "2021-08-06T01:17", "2021-08-06T01:17"),
                args("yyyy-MM-ddTHH:??:??", "2021-08-06T01", "2021-08-06T01", "2021-08-06T01"),
                args("yyyy-??-??T??:??:??.???Z", "2021-08-06T01:17:35.654Z", "2021-08-06T01:17:35.654Z", "2021-08-06T01:17:35.654Z"),
                args("yyyy-??-??T??:??:??.???Z", "2021-08-06T01:17:35.654789Z", "2021-08-06T01:17:35.654789Z", "2021-08-06T01:17:35.654789Z"),
                args("yyyy-??-??T??:??:??.???", "2021-08-06T01:17:35.654", "2021-08-06T01:17:35.654+02:00", "2021-08-06T01:17:35.654+02:00"),
                args("yyyy-??-??T??:??:??.???", "2021-08-06T01:17:35.654789", "2021-08-06T01:17:35.654789+02:00", "2021-08-06T01:17:35.654789+02:00"),

                // milliseconds pattern
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35.000-04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35,654-04:00", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.654789-04:00", "2021-08-06T23:17:35.654789-04:00", "2021-08-06T23:17:35.654789-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35.000+04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35.000Z", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.000Z", "2021-08-06T23:17:35.000Z", "2021-08-06T23:17:35.000Z"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.65Z", "2021-08-06T23:17:35.650Z", "2021-08-06T23:17:35.650Z"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.654321Z", "2021-08-06T23:17:35.654321Z", "2021-08-06T23:17:35.654321Z"),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35", "2021-08-06T23:17:35.000+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSSZ", "2021-08-06T23:17:35.654", "2021-08-06T23:17:35.654+02:00", CONVERSION_EXCEPTION),

                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35.000-04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35.000+04:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00"),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35.000Z", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z"),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35", "2021-08-06T23:17:35.000+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35.654", "2021-08-06T23:17:35.654+02:00", "2021-08-06T23:17:35.654+02:00"),

                // optional milliseconds pattern
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z"),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35", "2021-08-06T23:17:35+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35.654", "2021-08-06T23:17:35.654+02:00", CONVERSION_EXCEPTION),

                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00", "2021-08-06T23:17:35-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00", "2021-08-06T23:17:35.654-04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35+04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00", "2021-08-06T23:17:35.654+04:00"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35", "2021-08-06T23:17:35+02:00", "2021-08-06T23:17:35+02:00"),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35.654", "2021-08-06T23:17:35.654+02:00", "2021-08-06T23:17:35.654+02:00"),

                // partial patterns with only required fields, datetime in timezone
                args("yyyy-mm-ddTHH:MMZ", "2021-08-06T23:17-04:00", "2021-08-06T23:17-04:00", "2021-08-06T23:17-04:00"),
                args("yyyy-mm-ddTHH:MMZ", "2021-08-06T23:17Z", "2021-08-06T23:17Z", "2021-08-06T23:17Z"),
                args("yyyy-mm-ddTHH:MMZ", "2021-08-06T23:17", "2021-08-06T23:17+02:00", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:MM", "2021-08-06T23:17-04:00", "2021-08-06T23:17-04:00", "2021-08-06T23:17-04:00"),
                args("yyyy-mm-ddTHH:MM", "2021-08-06T23:17Z", "2021-08-06T23:17Z", "2021-08-06T23:17Z"),
                args("yyyy-mm-ddTHH:MM", "2021-08-06T23:17", "2021-08-06T23:17", "2021-08-06T23:17"),

                args("yyyy-mm-ddTHHZ", "2021-08-06T23:17-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHHZ", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHHZ", "2021-08-06T23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH", "2021-08-06T23:17-04:00", "2021-08-07T05", CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH", "2021-08-06T23-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH", "2021-08-06T23", "2021-08-06T23", "2021-08-06T23"),

                args("yyyy-mm-ddZ", "2021-08-06Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-mm-dd", "2021-08-06T23:17:35-04:00", "2021-08-07", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T13:17:35-04:00", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T00:17:35+04:00", "2021-08-05", CONVERSION_EXCEPTION),

                args("yyyy-mm", "2021-08", "2021-08", "2021-08"),
                args("yyyy-mm", "2021-08-31T23:17:35-04:00", "2021-09", CONVERSION_EXCEPTION),
                args("yyyy-mm", "2021-08-06T13:17:35-04:00", "2021-08", CONVERSION_EXCEPTION),
                args("yyyy-mm", "2021-08-01T00:17:35+04:00", "2021-07", CONVERSION_EXCEPTION),

                args("yyyy", "2021", "2021", "2021"),
                args("yyyy", "2021-08", "2021", CONVERSION_EXCEPTION),
                args("yyyy", "2021-12-31T23:17:35-04:00", "2022", CONVERSION_EXCEPTION),
                args("yyyy", "2021-08-06T13:17:35-04:00", "2021", CONVERSION_EXCEPTION),
                args("yyyy", "2021-01-01T00:17:35+04:00", "2020", CONVERSION_EXCEPTION),

                // compact pattern
                args("yyyymmddTHHMMSSZ", "2021-08-06T01:17:35Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymmddTHHMMSSZ", "20210806T011735", "20210806T011735+0200", CONVERSION_EXCEPTION),

                args("yyyymmddTHHMMSS", "2021-08-06T01:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymmddTHHMMSS", "20210806T011735", "20210806T011735+0200", "20210806T011735+0200"),

                args("yyyy-mm-ddTHH:MM:SSZ", "20210806T011735", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                args("yyyymmddTHHMM??", "2021-08-06T01:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymmddTHHMM??", "20210806T011735", "20210806T011735+0200", "20210806T011735+0200"),
                args("yyyymmddTHHMM??", "20210806T0117", "20210806T0117", "20210806T0117"),

                args("yyyymm??T??????", "2021-08-06T01", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymm??T??????", "20210806T01", "20210806T01", "20210806T01"),

                args("yyyymmddTHHMMXX", "2021-08-06T01:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymmddTHHMMXX", "20210806T0117", "20210806T0117", "20210806T0117"),

                args("yyyymmXXTXXXXXX", "2021-08", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymmXXTXXXXXX", "202108", "202108", "202108"),

                // invalid separator position
                args("YYYY-MM-DDTHH:MM:SS", "2021-08-06T01:17:", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("YYYY-MM-DDTHH:MM:SS", "2021-08-06T01:", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("YYYY-MM-DDTHH:MM:SS", "2021-08-", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("YYYY-MM-DDTHH:MM:SS", "2021-", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17:", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-??-??T??:??:??", "2021-08-06T01:", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-??-??T??:??:??", "2021-08-", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-??-??T??:??:??", "2021-", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // undefined pattern
                args("", "2021-08-06T23:17:35.000Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("", "2021-08-06T23:17:35.65Z", "2021-08-06T23:17:35.65Z", "2021-08-06T23:17:35.65Z"),
                args("", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z"),
                args("", "2021-08-06T23:17:35.654321Z", "2021-08-06T23:17:35.654321Z", "2021-08-06T23:17:35.654321Z"),
                args("", "2021-08-06T23:17:35.000+04:00", "2021-08-06T23:17:35+04:00", "2021-08-06T23:17:35+04:00"),
                args(null, "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z", "2021-08-06T23:17:35.654Z"),
                args("", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args(null, "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z", "2021-08-06T23:17:35Z"),
                args("", "2021-08-06T23:17:35.654", "2021-08-06T23:17:35.654+02:00", "2021-08-06T23:17:35.654+02:00"),
                args("", "2021-08-06T23:17:35", "2021-08-06T23:17:35+02:00", "2021-08-06T23:17:35+02:00"),
                args("", "2021-08-06T23:17Z", "2021-08-06T23:17Z", "2021-08-06T23:17Z"),
                args("", "2021-08-06T04:03:02Z", "2021-08-06T04:03:02Z", "2021-08-06T04:03:02Z"),
                args("", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "2021-08-06", "2021-08-06", "2021-08-06"),

                args("", "23:17:35.654+04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "23:17:35.654Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args(null, "23:17:35.654Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "23:17:35Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "23:17:35.654", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "23:17Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // localized date
                args("", "8/6/2021 4:03:02", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "08/06/2021 04:03:02", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "06.08.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "06.08.2021 04:03:02", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "06.08.2021 04:03:02+02:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "08.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "6.8.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "06.08.2021 2:03:04", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "6.8.2021 2:3:4", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "6.8.2021 04:03:02", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:mm:SS", "06.08.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:mm:SS", "06.08.2021 04:03:02", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-ddTHH:mm:SS", "06.08.2021 04:03:02+02:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
        )

        @JvmStatic
        @Suppress("unused")
        fun providePatternDateTimeAndExpectedTemporal(): Stream<Arguments> = Stream.of(
                // UTC datetime pattern
                args("yyyy-mm-ddTHH:MM:SSZ", "2021-08-06T23:17:35-04:00", OffsetDateTime.of(2021, 8, 6, 23, 17, 35, 0, ZoneOffset.ofHours(-4))),

                // local datetime pattern
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T23:17:35-04:00", OffsetDateTime.of(2021, 8, 6, 23, 17, 35, 0, ZoneOffset.ofHours(-4))),
                args("yyyy-mm-ddTHH:MM:SS", "2021-08-06T01:17:35", LocalDateTime.of(2021, 8, 6, 1, 17, 35, 0)),

                // optional seconds pattern
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T23:17:35-04:00", OpenEhrOffsetDateTime.of(2021, 8, 6, 23, 17, 35, null, ZoneOffset.ofHours(-4))),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17:35", OpenEhrLocalDateTime.of(2021, 8, 6, 1, 17, 35)),
                args("yyyy-mm-ddTHH:MM:??", "2021-08-06T01:17", OpenEhrLocalDateTime.of(2021, 8, 6, 1, 17)),

                // not allowed seconds pattern
                args("yyyy-mm-ddTHH:MM:XX", "2021-08-06T01:17", OpenEhrLocalDateTime.of(2021, 8, 6, 1, 17)),

                // optional hour and minute and not allowed seconds pattern
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01:17", OpenEhrLocalDateTime.of(2021, 8, 6, 1, 17)),
                args("yyyy-mm-ddT??:??:XX", "2021-08-06T01", OpenEhrLocalDateTime.of(2021, 8, 6, 1)),

                // not allowed pattern
                args("yyyy-XX-XXTXX:XX:XX", "2021", Year.of(2021)),
                args("yyyy-MM-ddTXX:XX:XX", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("yyyy-MM-ddTHH:XX:XX", "2021-08-06T01", OpenEhrLocalDateTime.of(2021, 8, 6, 1)),

                // optional pattern
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17:35", OpenEhrLocalDateTime.of(2021, 8, 6, 1, 17, 35)),
                args("yyyy-??-??T??:??:??", "2021-08-06T01:17", OpenEhrLocalDateTime.of(2021, 8, 6, 1, 17)),
                args("yyyy-??-??T??:??:??", "2021-08-06T01", OpenEhrLocalDateTime.of(2021, 8, 6, 1)),
                args("yyyy-??-??T??:??:??", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("yyyy-??-??T??:??:??", "2021-08", YearMonth.of(2021, 8)),
                args("yyyy-??-??T??:??:??", "2021", Year.of(2021)),
                args("yyyy-MM-??T??:??:??", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("yyyy-MM-??T??:??:??", "2021-08", YearMonth.of(2021, 8)),
                args("yyyy-MM-ddT??:??:??", "2021-08-06", LocalDate.of(2021, 8, 6)),

                // milliseconds pattern
                args(
                        "yyyy-mm-ddTHH:MM:SS.SSSZ",
                        "2021-08-06T23:17:35.654-04:00",
                        OffsetDateTime.of(2021, 8, 6, 23, 17, 35, 654_000_000, ZoneOffset.ofHours(-4))),
                args("yyyy-mm-ddTHH:MM:SS.SSS", "2021-08-06T23:17:35.654", LocalDateTime.of(2021, 8, 6, 23, 17, 35, 654_000_000)),

                // optional milliseconds pattern
                args("yyyy-mm-ddTHH:MM:SS.???Z", "2021-08-06T23:17:35-04:00", OpenEhrOffsetDateTime.of(2021, 8, 6, 23, 17, 35, null, ZoneOffset.ofHours(-4))),
                args(
                        "yyyy-mm-ddTHH:MM:SS.???Z",
                        "2021-08-06T23:17:35.654-04:00",
                        OffsetDateTime.of(2021, 8, 6, 23, 17, 35, 654_000_000, ZoneOffset.ofHours(-4))),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35", OpenEhrLocalDateTime.of(2021, 8, 6, 23, 17, 35)),
                args("yyyy-mm-ddTHH:MM:SS.???", "2021-08-06T23:17:35.654", LocalDateTime.of(2021, 8, 6, 23, 17, 35, 654_000_000)),

                // undefined pattern
                args("", "2021-08-06T23:17:35.000-04:00", OffsetDateTime.of(2021, 8, 6, 23, 17, 35, 0, ZoneOffset.ofHours(-4))),
                args("", "2021-08-06T23:17:35.654", LocalDateTime.of(2021, 8, 6, 23, 17, 35, 654_000_000)),
                args("", "2021-08-06T23:17:35", OpenEhrLocalDateTime.of(2021, 8, 6, 23, 17, 35)),
                args("", "2021-08-06T23:17", OpenEhrLocalDateTime.of(2021, 8, 6, 23, 17)),
                args("", "2021-08-06T23", OpenEhrLocalDateTime.of(2021, 8, 6, 23)),
                args("", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("", "2021-08", YearMonth.of(2021, 8)),
                args("", "2021", Year.of(2021))
        )

        private fun args(pattern: String?, dateTime: String, resultInLenientMode: String, resultInStrictMode: String) =
            Arguments.of(pattern, dateTime, resultInLenientMode, resultInStrictMode)

        private fun args(pattern: String?, dateTime: String, expectedParsedDateTime: Temporal) = Arguments.of(pattern, dateTime, expectedParsedDateTime)
    }

    @ParameterizedTest
    @MethodSource("providePatternDateTimeAndExpectedResult")
    fun handleDateTimeInLenientMode(pattern: String?, dateTime: String, expectedResultInLenientMode: String, expectedResultInStrictMode: String) {
        handleDateTime(pattern, dateTime, expectedResultInLenientMode, false)
    }

    @ParameterizedTest
    @MethodSource("providePatternDateTimeAndExpectedResult")
    fun handleDateTimeInStrictMode(pattern: String?, dateTime: String, expectedResultInLenientMode: String, expectedResultInStrictMode: String) {
        handleDateTime(pattern, dateTime, expectedResultInStrictMode, true)
    }

    @ParameterizedTest
    @MethodSource("providePatternDateTimeAndExpectedTemporal")
    fun parseDateTime(pattern: String?, dateTimeString: String, expectedParsedDateTime: Temporal) {
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern, true)
        val actualResult = formatter.parseDateTime(dateTimeString)
        assertThat(actualResult).describedAs("Parsing \"$dateTimeString\" using pattern \"$pattern\"").isEqualTo(expectedParsedDateTime)
    }

    @Test
    fun parsePartialDate() {
        val pattern = "yyyy-??-??"
        val dateString = "2021"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDateTime(dateString)).isInstanceOf(Year::class.java)

        val year = formatter.parseDateTime(dateString) { it, _, _ -> Year.from(it) }
        assertThat(year).isInstanceOf(Year::class.java)
        assertThat(year.value).isEqualTo(2021)

        val partialLocalDate =
            formatter.parseDateTime(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isFalse
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isFalse
    }

    @Test
    fun parseDate() {
        val pattern = "yyyy-MM-dd"
        val dateString = "2021-02-03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        val partialLocalDateTime =
            formatter.parseDateTime(dateString) { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context) }
        assertThat(partialLocalDateTime).isInstanceOf(OpenEhrLocalDateTime::class.java)
        assertThat((partialLocalDateTime).dateTime.year).isEqualTo(2021)
        assertThat(partialLocalDateTime.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDateTime.dateTime.monthValue).isEqualTo(2)
        assertThat(partialLocalDateTime.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDateTime.dateTime.dayOfMonth).isEqualTo(3)
        assertThat(partialLocalDateTime.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
    }

    @Test
    fun parsePartialDateTime() {
        val pattern = "yyyy-MM-ddTHH:??:??"
        val dateString = "2021-02-03T04:05"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDateTime(dateString)).isInstanceOf(TemporalAccessor::class.java)
        val partialLocalDateTime =
            formatter.parseDateTime(dateString) { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context) }

        assertThat(partialLocalDateTime).isInstanceOf(OpenEhrLocalDateTime::class.java)
        assertThat(partialLocalDateTime.dateTime.year).isEqualTo(2021)
        assertThat(partialLocalDateTime.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDateTime.dateTime.monthValue).isEqualTo(2)
        assertThat(partialLocalDateTime.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDateTime.dateTime.dayOfMonth).isEqualTo(3)
        assertThat(partialLocalDateTime.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
        assertThat(partialLocalDateTime.dateTime.hour).isEqualTo(4)
        assertThat(partialLocalDateTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalDateTime.dateTime.minute).isEqualTo(5)
        assertThat(partialLocalDateTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalDateTime.dateTime.second).isEqualTo(0)
        assertThat(partialLocalDateTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isFalse
    }

    @Test
    fun parseDateTime() {
        val pattern = "yyyy-MM-ddTHH:MM:SS"
        val dateString = "2021-02-03T04:05:06"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDateTime(dateString)).isInstanceOf(TemporalAccessor::class.java)
        val localDateTime =
            formatter.parseDateTime(dateString) { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context).dateTime }

        assertThat(localDateTime).isInstanceOf(LocalDateTime::class.java)
        assertThat(localDateTime.year).isEqualTo(2021)
        assertThat(localDateTime.isSupported(ChronoField.YEAR)).isTrue
        assertThat(localDateTime.monthValue).isEqualTo(2)
        assertThat(localDateTime.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(localDateTime.dayOfMonth).isEqualTo(3)
        assertThat(localDateTime.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
        assertThat(localDateTime.hour).isEqualTo(4)
        assertThat(localDateTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(localDateTime.minute).isEqualTo(5)
        assertThat(localDateTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(localDateTime.second).isEqualTo(6)
        assertThat(localDateTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isTrue
    }

    @Test
    fun parseDateBest() {
        val pattern = "yyyy-MM-ddTHH:MM:??"
        val dateString = "2021-02-03T04:05"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        val partialLocalDateTime = formatter.parseBestDateTime(
                dateString,
                { temporal, _, _ -> LocalDateTime.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context) },
                { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context).dateTime })

        assertThat(partialLocalDateTime).isInstanceOf(OpenEhrLocalDateTime::class.java)
        assertThat((partialLocalDateTime as OpenEhrLocalDateTime).dateTime.year).isEqualTo(2021)
        assertThat(partialLocalDateTime.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDateTime.dateTime.monthValue).isEqualTo(2)
        assertThat(partialLocalDateTime.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDateTime.dateTime.dayOfMonth).isEqualTo(3)
        assertThat(partialLocalDateTime.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
        assertThat(partialLocalDateTime.dateTime.hour).isEqualTo(4)
        assertThat(partialLocalDateTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalDateTime.dateTime.minute).isEqualTo(5)
        assertThat(partialLocalDateTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalDateTime.dateTime.second).isEqualTo(0)
        assertThat(partialLocalDateTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isFalse

        val localDateTime = formatter.parseBestDateTime(
                dateString,
                { temporal, _, _ -> LocalDateTime.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context).dateTime },
                { temporal, precisionField, context -> OpenEhrLocalDateTime.from(temporal, precisionField, context) })

        assertThat(localDateTime).isInstanceOf(LocalDateTime::class.java)
        assertThat((localDateTime as LocalDateTime).second).isEqualTo(0)
        assertThat(localDateTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isTrue
    }

    private fun handleDateTime(pattern: String?, dateTime: String, expectedResult: String, strictMode: Boolean) {
        if (expectedResult == CONVERSION_EXCEPTION) {
            assertThatExceptionOfType(DateTimeException::class.java)
                .describedAs("Parsing \"$dateTime\" using pattern \"$pattern\" in ${if (strictMode) "strict" else "lenient"} mode.")
                .isThrownBy {
                    parseAndFormat(dateTime, pattern, strictMode)
                }
        } else {
            val actualResult = parseAndFormat(dateTime, pattern, strictMode)
            assertThat(actualResult)
                .describedAs("Parsing \"$dateTime\" using pattern \"$pattern\" in ${if (strictMode) "strict" else "lenient"} mode.").isEqualTo(expectedResult)
        }
    }

    private fun parseAndFormat(dateTimeString: String, pattern: String?, strictMode: Boolean): String {
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern, strictMode)
        val dateTime = formatter.parseDateTime(dateTimeString)
        return formatter.format(dateTime)
    }
}
