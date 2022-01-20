package care.better.platform.time.format

import care.better.platform.time.temporal.OpenEhrLocalDate
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
class OpenEhrDateFormattingTest {

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
        fun providePatternDateAndExpectedResult(): Stream<Arguments> = Stream.of(
                // full date pattern
                args("yyyy-mm-dd", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-mm-dd", "2021-08", "2021-08-01", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021", "2021-01-01", CONVERSION_EXCEPTION),

                // year, month pattern
                args("yyyy-mm", "2021-08-06", "2021-08", CONVERSION_EXCEPTION),
                args("yyyy-mm", "2021-08", "2021-08", "2021-08"),
                args("yyyy-mm", "2021", "2021-01", CONVERSION_EXCEPTION),

                // year pattern
                args("yyyy", "2021-08-06", "2021", CONVERSION_EXCEPTION),
                args("yyyy", "2021-08", "2021", CONVERSION_EXCEPTION),
                args("yyyy", "2021", "2021", "2021"),

                // optional day pattern
                args("yyyy-mm-??", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-mm-??", "2021-08", "2021-08", "2021-08"),
                args("yyyy-mm-??", "2021", "2021-01", CONVERSION_EXCEPTION),

                // optional month and day pattern
                args("yyyy-??-??", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-??-??", "2021-08", "2021-08", "2021-08"),
                args("yyyy-??-??", "2021", "2021", "2021"),

                // not allowed day pattern
                args("yyyy-mm-xx", "2021-08-06", "2021-08", CONVERSION_EXCEPTION),
                args("yyyy-mm-xx", "2021-08", "2021-08", "2021-08"),
                args("yyyy-mm-xx", "2021", "2021-01", CONVERSION_EXCEPTION),

                // not allowed day with optional month pattern
                args("yyyy-??-xx", "2021-08-06", "2021-08", CONVERSION_EXCEPTION),
                args("yyyy-??-xx", "2021-08", "2021-08", "2021-08"),
                args("yyyy-??-xx", "2021", "2021", "2021"),

                // not allowed month and day pattern
                args("yyyy-xx-xx", "2021-08-06", "2021", CONVERSION_EXCEPTION),
                args("yyyy-xx-xx", "2021-08", "2021", CONVERSION_EXCEPTION),
                args("yyyy-xx-xx", "2021", "2021", "2021"),

                // case insensitive pattern
                args("yyyy-mm-dd", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("YYYY-MM-DD", "2021-08-06", "2021-08-06", "2021-08-06"),
                args("yyyy-mm-xx", "2021-08-06", "2021-08", CONVERSION_EXCEPTION),
                args("YYYY-MM-XX", "2021-08-06", "2021-08", CONVERSION_EXCEPTION),

                // compact pattern
                args("yyyymmdd", "2021-08-06", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyymmdd", "20210806", "20210806", "20210806"),

                args("yyyy-mm-dd", "20210806T011735", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "20210806", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                args("yyyy??XX", "2021-08", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy??XX", "202108", "202108", "202108"),

                // date with time
                args("yyyy-mm-dd", "2021-08-06T23:17:35-04:00", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T01:17:35+04:00", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T23:17:35.654Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T23:17:35Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T23Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06TZ", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06T", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-08-06Z", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // invalid separator position
                args("yyyy-mm-dd", "2021-08-", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "2021-", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),

                // undefined pattern
                args("", "2021-08-06T23:17:35.654Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("", "2021-08-06T23:17:35Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("", "2021-08-06T23Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("", "2021-08-06", "2021-08-06", "2021-08-06"),
                args(null, "2021-08-06", "2021-08-06", "2021-08-06"),
                args("", "2021-08-06", "2021-08-06", "2021-08-06"),
                args(null, "2021-08", "2021-08", "2021-08"),
                args("", "2021", "2021", "2021"),
                args(null, "2021", "2021", "2021"),

                // undefined pattern
                args("", "2021-08-06T23:17:35.654Z", "2021-08-06", CONVERSION_EXCEPTION),
                args("", "2021-08-06", "2021-08-06", "2021-08-06"),
                args(null, "2021-08-06", "2021-08-06", "2021-08-06"),
                args("", "2021-08", "2021-08", "2021-08"),
                args("", "2021", "2021", "2021"),

                // localized date
                args("", "8/6/2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "08/06/2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "06.08.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("", "6.8.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "06.08.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
                args("yyyy-mm-dd", "6.8.2021", CONVERSION_EXCEPTION, CONVERSION_EXCEPTION),
        )


        @JvmStatic
        @Suppress("unused")
        fun providePatternDateAndExpectedTemporal(): Stream<Arguments> = Stream.of(
                args("yyyy-mm-dd", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("yyyy-mm", "2021-08", YearMonth.of(2021, 8)),
                args("yyyy", "2021", Year.of(2021)),

                args("yyyy-mm-??", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("yyyy-mm-??", "2021-08", YearMonth.of(2021, 8)),

                args("yyyy-??-??", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("yyyy-??-??", "2021-08", YearMonth.of(2021, 8)),
                args("yyyy-??-??", "2021", Year.of(2021)),

                args("yyyy-mm-xx", "2021-08", YearMonth.of(2021, 8)),

                args("yyyy-??-xx", "2021-08", YearMonth.of(2021, 8)),
                args("yyyy-??-xx", "2021", Year.of(2021)),

                args("yyyy-xx-xx", "2021", Year.of(2021)),

                // undefined pattern
                args("", "2021-08-06", LocalDate.of(2021, 8, 6)),
                args("", "2021-08", YearMonth.of(2021, 8)),
                args("", "2021", Year.of(2021)),
        )

        private fun args(pattern: String?, date: String, resultInLenientMode: String, resultInStrictMode: String) =
            Arguments.of(pattern, date, resultInLenientMode, resultInStrictMode)

        private fun args(pattern: String?, date: String, expectedParsedDate: Temporal) = Arguments.of(pattern, date, expectedParsedDate)
    }

    @ParameterizedTest
    @MethodSource("providePatternDateAndExpectedResult")
    fun handleDateInLenientMode(pattern: String?, date: String, expectedResultInLenientMode: String, expectedResultInStrictMode: String) {
        handleDate(pattern, date, expectedResultInLenientMode, false)
    }

    @ParameterizedTest
    @MethodSource("providePatternDateAndExpectedResult")
    fun handleDateInStrictMode(pattern: String?, date: String, expectedResultInLenientMode: String, expectedResultInStrictMode: String) {
        handleDate(pattern, date, expectedResultInStrictMode, true)
    }

    @ParameterizedTest
    @MethodSource("providePatternDateAndExpectedTemporal")
    fun parseDate(pattern: String?, dateString: String, expectedParsedDate: Temporal) {
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern, true)
        val actualResult = formatter.parseDate(dateString)
        assertThat(actualResult).describedAs("Parsing \"$dateString\" using pattern \"$pattern\"").isEqualTo(expectedParsedDate)
    }

    @Test
    fun parsePartialYear() {
        val pattern = "yyyy-??-??"
        val dateString = "2021"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDate(dateString)).isInstanceOf(TemporalAccessor::class.java)
        assertThat(formatter.parseDate(dateString)).isInstanceOf(Year::class.java)

        val year = formatter.parseDate(dateString) { it, _, _ -> Year.from(it) }
        assertThat(year).isInstanceOf(Year::class.java)
        assertThat(year.value).isEqualTo(2021)

        val partialLocalDate =
            formatter.parseDate(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isFalse
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isFalse
    }

    @Test
    fun parseYear() {
        val pattern = "yyyy"
        val dateString = "2021"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDate(dateString)).isInstanceOf(TemporalAccessor::class.java)
        val year = formatter.parseDate(dateString) { temporal, _, _ -> Year.from(temporal) }

        assertThat(year).isInstanceOf(Year::class.java)
        assertThat(year.value).isEqualTo(2021)

        val partialLocalDate =
            formatter.parseDate(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isFalse
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isFalse
    }

    @Test
    fun parsePartialYearMonth() {
        val pattern = "yyyy-??-??"
        val dateString = "2021-10"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDate(dateString)).isInstanceOf(TemporalAccessor::class.java)
        assertThat(formatter.parseDate(dateString)).isInstanceOf(YearMonth::class.java)
        val yearMonth = formatter.parseDate(dateString) { it, _, _ -> YearMonth.from(it) }

        assertThat(yearMonth).isInstanceOf(YearMonth::class.java)
        assertThat(yearMonth.year).isEqualTo(2021)
        assertThat(yearMonth.monthValue).isEqualTo(10)

        val partialLocalDate =
            formatter.parseDate(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(10)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isFalse
    }

    @Test
    fun parseYearMonth() {
        val pattern = "yyyy-MM"
        val dateString = "2021-10"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDate(dateString)).isInstanceOf(TemporalAccessor::class.java)
        val yearMonth = formatter.parseDate(dateString) { it, _, _ -> YearMonth.from(it) }

        assertThat(yearMonth).isInstanceOf(YearMonth::class.java)
        assertThat(yearMonth.year).isEqualTo(2021)
        assertThat(yearMonth.monthValue).isEqualTo(10)

        val partialLocalDate =
            formatter.parseDate(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(10)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isFalse
    }

    @Test
    fun parsePartialLocalDate() {
        val pattern = "yyyy-??-??"
        val dateString = "2021-10-03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDate(dateString)).isInstanceOf(TemporalAccessor::class.java)
        assertThat(formatter.parseDate(dateString)).isInstanceOf(LocalDate::class.java)
        val localDate = formatter.parseDate(dateString) { it, _, _ -> LocalDate.from(it) }

        assertThat(localDate).isInstanceOf(LocalDate::class.java)
        assertThat(localDate.year).isEqualTo(2021)
        assertThat(localDate.monthValue).isEqualTo(10)
        assertThat(localDate.dayOfMonth).isEqualTo(3)

        val partialLocalDate =
            formatter.parseDate(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(10)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(3)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
    }

    @Test
    fun parseLocalDate() {
        val pattern = "yyyy-MM-dd"
        val dateString = "2021-10-03"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        assertThat(formatter.parseDate(dateString)).isInstanceOf(TemporalAccessor::class.java)
        val localDate = formatter.parseDate(dateString) { temporal, _, _ -> LocalDate.from(temporal) }

        assertThat(localDate).isInstanceOf(LocalDate::class.java)
        assertThat(localDate.year).isEqualTo(2021)
        assertThat(localDate.monthValue).isEqualTo(10)
        assertThat(localDate.dayOfMonth).isEqualTo(3)

        val partialLocalDate =
            formatter.parseDate(dateString) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat(partialLocalDate.date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(10)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(3)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
    }

    @Test
    fun parseDateBest() {
        val pattern = "yyyy-MM-??"
        val dateString = "2021-10"
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern)

        val partialLocalDate = formatter.parseBestDate(
                dateString,
                { temporal, _, _ -> LocalTime.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) },
                { temporal, _, _ -> YearMonth.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) })

        assertThat(partialLocalDate).isInstanceOf(OpenEhrLocalDate::class.java)
        assertThat((partialLocalDate as OpenEhrLocalDate).date.year).isEqualTo(2021)
        assertThat(partialLocalDate.isSupported(ChronoField.YEAR)).isTrue
        assertThat(partialLocalDate.date.monthValue).isEqualTo(10)
        assertThat(partialLocalDate.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(partialLocalDate.date.dayOfMonth).isEqualTo(1)
        assertThat(partialLocalDate.isSupported(ChronoField.DAY_OF_MONTH)).isFalse

        val yearMonth = formatter.parseBestDate(
                dateString,
                { temporal, _, _ -> LocalTime.from(temporal) },
                { temporal, _, _ -> YearMonth.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) })

        assertThat(yearMonth).isInstanceOf(YearMonth::class.java)
        assertThat((yearMonth as YearMonth).monthValue).isEqualTo(10)
        assertThat(yearMonth.isSupported(ChronoField.MONTH_OF_YEAR)).isTrue
        assertThat(yearMonth.isSupported(ChronoField.DAY_OF_MONTH)).isFalse

        val localDate = formatter.parseBestDate(
                dateString,
                { temporal, _, _ -> LocalTime.from(temporal) },
                { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context).date },
                { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) })

        assertThat(localDate).isInstanceOf(LocalDate::class.java)
        assertThat((localDate as LocalDate).dayOfMonth).isEqualTo(1)
        assertThat(localDate.isSupported(ChronoField.DAY_OF_MONTH)).isTrue
    }

    private fun handleDate(pattern: String?, date: String, expectedResult: String, strictMode: Boolean) {
        if (expectedResult == CONVERSION_EXCEPTION) {
            assertThatExceptionOfType(DateTimeException::class.java)
                .describedAs("Parsing \"$date\" using pattern \"$pattern\" in ${if (strictMode) "strict" else "lenient"} mode.")
                .isThrownBy {
                    parseAndFormat(date, pattern, strictMode)
                }
        } else {
            val actualResult = parseAndFormat(date, pattern, strictMode)
            assertThat(actualResult)
                .describedAs("Parsing \"$date\" using pattern \"$pattern\" in ${if (strictMode) "strict" else "lenient"} mode.").isEqualTo(expectedResult)
        }
    }

    private fun parseAndFormat(dateString: String, pattern: String?, strictMode: Boolean): String {
        val formatter = OpenEhrDateTimeFormatter.ofPattern(pattern, strictMode)
        val date = formatter.parseDate(dateString)
        return formatter.format(date)
    }
}
