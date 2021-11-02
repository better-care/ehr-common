package care.better.platform.time.format

import care.better.platform.time.temporal.OpenEhrLocalTime
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAccessor
import java.util.*
import java.util.stream.Stream

/**
 * @author Matic Ribic
 */
class OpenEhrTimeFormattingTest {

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
        fun providePatternTimeAndExpectedResult(): Stream<Arguments> = Stream.of(
                // full time pattern
                args("HH:MM:SSZ", "23:17:35-04:00", "23:17:35-04:00", "23:17:35-04:00"),
                args("HH:MM:SSZ", "23:17:35-0400", "23:17:35-04:00", "23:17:35-04:00"),
                args("HH:MM:SSZ", "23:17:35-04", "23:17:35-04:00", "23:17:35-04:00"),
                args("HH:MM:SSZ", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:MM:SSZ", "23:17Z", "23:17:00Z", CONVERSION_EXCEPTION),
                args("HH:MM:SSZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SSZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SSZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SSZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "23:17:35-04:00", "23:17:35-04:00", "23:17:35-04:00"),
                args("HH:MM:SS", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:MM:SS", "23:17Z", "23:17:00Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "23:17:35", "23:17:35", "23:17:35"),
                args("HH:MM:SS", "23:17", "23:17:00", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "23", "23:00:00", CONVERSION_EXCEPTION),

                // hour, minute pattern
                args("HH:MMZ", "23:17:35-04:00", "23:17-04:00", CONVERSION_EXCEPTION),
                args("HH:MMZ", "23:17:35Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:MMZ", "23:17Z", "23:17Z", "23:17Z"),
                args("HH:MMZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MMZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MMZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MMZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM", "23:17:35-04:00", "23:17-04:00", CONVERSION_EXCEPTION),
                args("HH:MM", "23:17:35Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:MM", "23:17Z", "23:17Z", "23:17Z"),
                args("HH:MM", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM", "23:17:35", "23:17", CONVERSION_EXCEPTION),
                args("HH:MM", "23:17", "23:17", "23:17"),
                args("HH:MM", "23", "23:00", CONVERSION_EXCEPTION),

                // hour pattern
                args("HHZ", "23:17:35-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHZ", "23:17:35Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHZ", "23:17Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH", "23:17:35-04:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH", "23:17:35Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH", "23:17Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH", "23:17:35", "23", CONVERSION_EXCEPTION),
                args("HH", "23:17", "23", CONVERSION_EXCEPTION),
                args("HH", "23", "23", "23"),

                // milliseconds pattern
                args("HH:MM:SS.SSSZ", "23:17:35.653-04:00", "23:17:35.653-04:00", "23:17:35.653-04:00"),
                args("HH:MM:SS.SSSZ", "23:17:35.653Z", "23:17:35.653Z", "23:17:35.653Z"),
                args("HH:MM:SS.SSSZ", "23:17:35Z", "23:17:35.000Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSSZ", "23:17Z", "23:17:00.000Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSSZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSSZ", "23:17:35.653", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSSZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSSZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSSZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSS", "23:17:35.653-04:00", "23:17:35.653-04:00", "23:17:35.653-04:00"),
                args("HH:MM:SS.SSS", "23:17:35.653Z", "23:17:35.653Z", "23:17:35.653Z"),
                args("HH:MM:SS.SSS", "23:17:35Z", "23:17:35.000Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSS", "23:17Z", "23:17:00.000Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSS", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSS", "23:17:35.653", "23:17:35.653", "23:17:35.653"),
                args("HH:MM:SS.SSS", "23:17:35,653", "23:17:35.653", "23:17:35.653"),
                args("HH:MM:SS.SSS", "23:17:35", "23:17:35.000", CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSS", "23:17", "23:17:00.000", CONVERSION_EXCEPTION),
                args("HH:MM:SS.SSS", "23", "23:00:00.000", CONVERSION_EXCEPTION),

                // optional milliseconds pattern
                args("HH:MM:SS.???Z", "23:17:35.653-04:00", "23:17:35.653-04:00", "23:17:35.653-04:00"),
                args("HH:MM:SS.???Z", "23:17:35.653Z", "23:17:35.653Z", "23:17:35.653Z"),
                args("HH:MM:SS.???Z", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:MM:SS.???Z", "23:17Z", "23:17:00Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS.???Z", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.???Z", "23:17:35.653", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.???Z", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.???Z", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.???Z", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.???", "23:17:35.653-04:00", "23:17:35.653-04:00", "23:17:35.653-04:00"),
                args("HH:MM:SS.???", "23:17:35.653Z", "23:17:35.653Z", "23:17:35.653Z"),
                args("HH:MM:SS.???", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:MM:SS.???", "23:17Z", "23:17:00Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS.???", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS.???", "23:17:35.653", "23:17:35.653", "23:17:35.653"),
                args("HH:MM:SS.???", "23:17:35", "23:17:35", "23:17:35"),
                args("HH:MM:SS.???", "23:17", "23:17:00", CONVERSION_EXCEPTION),
                args("HH:MM:SS.???", "23", "23:00:00", CONVERSION_EXCEPTION),

                // optional second pattern
                args("HH:MM:??Z", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:MM:??Z", "23:17Z", "23:17Z", "23:17Z"),
                args("HH:MM:??Z", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:??Z", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:??Z", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:??Z", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:??", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:MM:??", "23:17Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:MM:??", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:??", "23:17:35", "23:17:35", "23:17:35"),
                args("HH:MM:??", "23:17", "23:17", "23:17"),
                args("HH:MM:??", "23", "23:00", CONVERSION_EXCEPTION),

                // optional minute and second pattern
                args("HH:??:??Z", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:??:??Z", "23:17Z", "23:17Z", "23:17Z"),
                args("HH:??:??Z", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:??Z", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:??Z", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:??Z", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:??", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("HH:??:??", "23:17Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:??:??", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:??", "23:17:35", "23:17:35", "23:17:35"),
                args("HH:??:??", "23:17", "23:17", "23:17"),
                args("HH:??:??", "23", "23", "23"),

                // not allowed second pattern
                args("HH:MM:xxZ", "23:17:35Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:MM:xxZ", "23:17Z", "23:17Z", "23:17Z"),
                args("HH:MM:xxZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:xxZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:xxZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:xxZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:xx", "23:17:35Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:MM:xx", "23:17Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:MM:xx", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:xx", "23:17:35", "23:17", CONVERSION_EXCEPTION),
                args("HH:MM:xx", "23:17", "23:17", "23:17"),
                args("HH:MM:xx", "23", "23:00", CONVERSION_EXCEPTION),

                // not allowed second with optional minute pattern
                args("HH:??:xxZ", "23:17:35Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:??:xxZ", "23:17Z", "23:17Z", "23:17Z"),
                args("HH:??:xxZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:xxZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:xxZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:xxZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:xx", "23:17:35Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:??:xx", "23:17Z", "23:17Z", CONVERSION_EXCEPTION),
                args("HH:??:xx", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:??:xx", "23:17:35", "23:17", CONVERSION_EXCEPTION),
                args("HH:??:xx", "23:17", "23:17", "23:17"),
                args("HH:??:xx", "23", "23", "23"),

                // not allowed minute and second pattern
                args("HH:xx:xxZ", "23:17:35Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xxZ", "23:17Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xxZ", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xxZ", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xxZ", "23:17", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xxZ", "23", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xx", "23:17:35Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xx", "23:17Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xx", "23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:xx:xx", "23:17:35", "23", CONVERSION_EXCEPTION),
                args("HH:xx:xx", "23:17", "23", CONVERSION_EXCEPTION),
                args("HH:xx:xx", "23", "23", "23"),

                // case insensitive pattern
                args("HH:MM:SS", "23:17:35", "23:17:35", "23:17:35"),
                args("HH:MM:SS", "23:17:35", "23:17:35", "23:17:35"),
                args("HH:MM:xx", "23:17:35", "23:17", CONVERSION_EXCEPTION),
                args("HH:MM:XX", "23:17:35", "23:17", CONVERSION_EXCEPTION),

                // compact pattern
                args("HHMMSS", "23:17:35", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HHMMSS", "231735", "231735", "231735"),

                args("HH:MM:SS", "231735", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                args("HH??XX", "231735", "2317", CONVERSION_EXCEPTION),
                args("HH??XX", "2317", "2317", "2317"),

                // date with time
                args("HH:MM:SSZ", "2021-08-06T23:17:35-04:00", "23:17:35-04:00", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06T23:17:35-04:00", "23:17:35-04:00", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06T01:17:35+04:00", "01:17:35+04:00", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06T23:17:35.654Z", "23:17:35Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06T23:17:35Z", "23:17:35Z", CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06T23Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06TZ", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "2021-08-06Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // invalid separator position
                args("HH:MM:SS", "23:17:", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "23:", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // undefined pattern
                args("", "2021-08-06T23:17:35.654Z", "23:17:35.654Z", CONVERSION_EXCEPTION),
                args("", "23:17:35.654+04:00", "23:17:35.654+04:00", "23:17:35.654+04:00"),
                args("", "23:17:35.654Z", "23:17:35.654Z", "23:17:35.654Z"),
                args(null, "23:17:35.654Z", "23:17:35.654Z", "23:17:35.654Z"),
                args("", "23:17:35Z", "23:17:35Z", "23:17:35Z"),
                args("", "23:17:35.654", "23:17:35.654", "23:17:35.654"),
                args("", "23:17:35", "23:17:35", "23:17:35"),
                args("", "23:17Z", "23:17Z", "23:17Z"),
                args("", "23:17", "23:17", "23:17"),
                args("", "04:03:02+02:00", "04:03:02+02:00", "04:03:02+02:00"),

                // localized date
                args("", "02:03:04 Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "02:03:04 +02:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "02:03:04 Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("HH:MM:SS", "02:03:04 +02:00", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
        )

        private fun args(pattern: String?, Time: String, resultInLenientMode: String, resultInStrictMode: String) =
            Arguments.of(pattern, Time, resultInLenientMode, resultInStrictMode)
    }

    @ParameterizedTest
    @MethodSource("providePatternTimeAndExpectedResult")
    fun handleTimeInLenientMode(pattern: String?, time: String, expectedResultInLenientMode: String, expectedResultInStrictMode: String) {
        handleTime(pattern, time, expectedResultInLenientMode, false)
    }

    @ParameterizedTest
    @MethodSource("providePatternTimeAndExpectedResult")
    fun handleTimeInStrictMode(pattern: String?, time: String, expectedResultInLenientMode: String, expectedResultInStrictMode: String) {
        handleTime(pattern, time, expectedResultInStrictMode, true)
    }

    @Test
    fun parsePartialDayMinute() {
        val pattern = "HH:mm"
        val timeString = "02:03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseTime(timeString)).isInstanceOf(TemporalAccessor::class.java)
        val partialLocalTime =
            formatter.parseTime(timeString) { temporal, precisionField, context -> OpenEhrLocalTime.from(temporal, precisionField, context) }

        assertThat(partialLocalTime).isInstanceOf(OpenEhrLocalTime::class.java)
        assertThat(partialLocalTime.time.hour).isEqualTo(2)
        assertThat(partialLocalTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalTime.time.minute).isEqualTo(3)
        assertThat(partialLocalTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalTime.time.second).isEqualTo(0)
        assertThat(partialLocalTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isFalse
    }

    @Test
    fun parseDayMinute() {
        val pattern = "HH:mm"
        val timeString = "02:03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseTime(timeString)).isInstanceOf(TemporalAccessor::class.java)
        val partialLocalTime =
            formatter.parseTime(timeString) { temporal, precisionField, context -> OpenEhrLocalTime.from(temporal, precisionField, context) }

        assertThat(partialLocalTime).isInstanceOf(OpenEhrLocalTime::class.java)
        assertThat(partialLocalTime.time.hour).isEqualTo(2)
        assertThat(partialLocalTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalTime.time.minute).isEqualTo(3)
        assertThat(partialLocalTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalTime.time.second).isEqualTo(0)
        assertThat(partialLocalTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isFalse
    }

    @Test
    fun parsePartialLocalTime() {
        val pattern = "HH:??:??"
        val timeString = "02:03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseTime(timeString)).isInstanceOf(TemporalAccessor::class.java)
        assertThatThrownBy {
            val localTime = formatter.parseTime(timeString) { it, _, _ -> LocalTime.from(it) }
        }.isInstanceOf(DateTimeException::class.java)
        val partialLocalTime =
            formatter.parseTime(timeString) { temporal, precisionField, context -> OpenEhrLocalTime.from(temporal, precisionField, context) }

        assertThat(partialLocalTime).isInstanceOf(OpenEhrLocalTime::class.java)
        assertThat(partialLocalTime.time.hour).isEqualTo(2)
        assertThat(partialLocalTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalTime.time.minute).isEqualTo(3)
        assertThat(partialLocalTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalTime.time.second).isEqualTo(0)
        assertThat(partialLocalTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isFalse

        val localTime = formatter.parseTime(timeString) { temporalAccessor, precisionField, context ->
            OpenEhrLocalTime.from(
                    temporalAccessor,
                    precisionField,
                    context).time
        }
        assertThat(localTime).isInstanceOf(LocalTime::class.java)
        assertThat(localTime.hour).isEqualTo(2)
        assertThat(localTime.minute).isEqualTo(3)
        assertThat(localTime.second).isEqualTo(0)
    }

    @Test
    fun parseLocalTime() {
        val pattern = "HH:mm:SS"
        val timeString = "02:03:04"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseTime(timeString)).isInstanceOf(TemporalAccessor::class.java)
        assertThatThrownBy {
            val localTime = formatter.parseTime(timeString) { it, _, _ -> LocalTime.from(it) }
        }.isInstanceOf(DateTimeException::class.java)
        val partialLocalTime =
            formatter.parseTime(timeString) { temporalAccessor, precisionField, context -> OpenEhrLocalTime.from(temporalAccessor, precisionField, context) }

        assertThat(partialLocalTime).isInstanceOf(OpenEhrLocalTime::class.java)
        assertThat(partialLocalTime.time.hour).isEqualTo(2)
        assertThat(partialLocalTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalTime.time.minute).isEqualTo(3)
        assertThat(partialLocalTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalTime.time.second).isEqualTo(4)
        assertThat(partialLocalTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isTrue

        val localTime = formatter.parseTime(timeString) { temporalAccessor, precisionField, context ->
            OpenEhrLocalTime.from(
                    temporalAccessor,
                    precisionField,
                    context).time
        }
        assertThat(localTime).isInstanceOf(LocalTime::class.java)
        assertThat(localTime.hour).isEqualTo(2)
        assertThat(localTime.minute).isEqualTo(3)
        assertThat(localTime.second).isEqualTo(4)
    }

    @Test
    fun parseTimeBest() {
        val pattern = "HH:mm:??"
        val timeString = "02:03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        val partialLocalTime = formatter.parseBestTime(
                timeString,
                { temporal, _, _ -> LocalDate.from(temporal) },
                { temporal, _, _ -> LocalTime.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalTime.from(temporal, precisionField, context) })

        assertThat(partialLocalTime).isInstanceOf(OpenEhrLocalTime::class.java)
        assertThat((partialLocalTime as OpenEhrLocalTime).time.hour).isEqualTo(2)
        assertThat(partialLocalTime.isSupported(ChronoField.HOUR_OF_DAY)).isTrue
        assertThat(partialLocalTime.time.minute).isEqualTo(3)
        assertThat(partialLocalTime.isSupported(ChronoField.MINUTE_OF_HOUR)).isTrue
        assertThat(partialLocalTime.time.second).isEqualTo(0)
        assertThat(partialLocalTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isFalse


        val localTime = formatter.parseBestTime(
                timeString,
                { temporal, _, _ -> LocalDate.from(temporal) },
                { temporalAccessor, precisionField, context -> OpenEhrLocalTime.from(temporalAccessor, precisionField, context).time },
                { temporal, precisionField, context -> OpenEhrLocalTime.from(temporal, precisionField, context) })

        assertThat(localTime).isInstanceOf(LocalTime::class.java)
        assertThat((localTime as LocalTime).second).isEqualTo(0)
        assertThat(localTime.isSupported(ChronoField.SECOND_OF_MINUTE)).isTrue
    }

    private fun handleTime(pattern: String?, time: String, expectedResult: String, strictMode: Boolean) {
        if (expectedResult == CONVERSION_EXCEPTION) {
            assertThatExceptionOfType(DateTimeException::class.java)
                .describedAs("Parsing \"$time\" using pattern \"$pattern\" in ${if (strictMode) "strict" else "lenient"} mode.")
                .isThrownBy {
                    parseAndFormat(time, pattern, strictMode)
                }
        } else {
            val actualResult = parseAndFormat(time, pattern, strictMode)
            assertThat(actualResult)
                .describedAs("Parsing \"$time\" using pattern \"$pattern\" in ${if (strictMode) "strict" else "lenient"} mode.").isEqualTo(expectedResult)
        }
    }

    private fun parseAndFormat(timeString: String, pattern: String?, strictMode: Boolean): String {
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern, strictMode)
        val time = formatter.parseTime(timeString)
        return formatter.format(time)
    }
}
