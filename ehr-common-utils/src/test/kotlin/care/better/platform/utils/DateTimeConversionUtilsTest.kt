/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package care.better.platform.utils

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Period
import org.junit.jupiter.api.Test
import org.openehr.rm.datatypes.DvDateTime
import java.time.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class DateTimeConversionUtilsTest {
    @Test
    fun extendedZonedDateTime() {
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17+01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33+01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33+01"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17Z"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneId.of("Z")))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17.00191+01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 1910000, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17,00191+01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 1910000, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17.00000017"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 170, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2016-03-16T16:45:40.929+01:00"))
            .isEqualTo(ZonedDateTime.of(2016, 3, 16, 16, 45, 40, 929000000, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17,00191-01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 1910000, ZoneOffset.ofHours(-1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17.000000001"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 1, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17.1"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 100000000, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17.0"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:17.000000000"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneId.systemDefault()))
    }

    @Test
    fun extendedLocalDate() {
        assertThat(DateTimeConversionUtils.toLocalDate("2015-01-01+01:00"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("2015-01-01"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("2015-01-01T12:33+01"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
    }

    @Test
    fun extendedLocalTime() {
        assertThat(DateTimeConversionUtils.toLocalTime("12:33:17.0001+01:00"))
            .isEqualTo(LocalTime.of(12, 33, 17, 100000))
        assertThat(DateTimeConversionUtils.toLocalTime("2015-1-1T07:08:09"))
            .isEqualTo(LocalTime.of(7, 8, 9))
    }

    @Test
    fun partialExtendedLocalTime() {
        assertThat(DateTimeConversionUtils.toLocalTime("12:33:12.0000123+01:00"))
            .isEqualTo(LocalTime.of(12, 33, 12, 12300))
        assertThat(DateTimeConversionUtils.toLocalTime("12:33:12,1230000+01:00"))
            .isEqualTo(LocalTime.of(12, 33, 12, 123000000))
        assertThat(DateTimeConversionUtils.toLocalTime("12:33:12.123+01:00"))
            .isEqualTo(LocalTime.of(12, 33, 12, 123000000))
        assertThat(DateTimeConversionUtils.toLocalTime("12:33:12+01:00"))
            .isEqualTo(LocalTime.of(12, 33, 12))
        assertThat(DateTimeConversionUtils.toLocalTime("12:33+01:00"))
            .isEqualTo(LocalTime.of(12, 33))
        assertThat(DateTimeConversionUtils.toLocalTime("07+01:00"))
            .isEqualTo(LocalTime.of(7, 0))
        assertThat(DateTimeConversionUtils.toLocalTime("07-01:00"))
            .isEqualTo(LocalTime.of(7, 0))
        assertThat(DateTimeConversionUtils.toLocalTime("09"))
            .isEqualTo(LocalTime.of(9, 0))
    }

    @Test
    fun simpleLocalTime() {
        assertThat(DateTimeConversionUtils.toLocalTime("123317.0001+0100"))
            .isEqualTo(LocalTime.of(12, 33, 17, 100000))
        assertThat(DateTimeConversionUtils.toLocalTime("123317,0001+0100"))
            .isEqualTo(LocalTime.of(12, 33, 17, 100000))
        assertThat(DateTimeConversionUtils.toLocalTime("123317,0001-0100"))
            .isEqualTo(LocalTime.of(12, 33, 17, 100000))
        assertThat(DateTimeConversionUtils.toLocalTime("070809+0100"))
            .isEqualTo(LocalTime.of(7, 8, 9))
        assertThat(DateTimeConversionUtils.toLocalTime("070809"))
            .isEqualTo(LocalTime.of(7, 8, 9))
        assertThat(DateTimeConversionUtils.toLocalTime("201511T07:08:09"))
            .isEqualTo(LocalTime.of(7, 8, 9))
    }

    @Test
    fun offsetTime() {
        assertThat(DateTimeConversionUtils.toOffsetTime("12:30"))
            .isEqualTo(OffsetTime.of(12, 30, 0, 0, ZoneOffset.UTC))
        assertThat(DateTimeConversionUtils.toOffsetTime("12:30:11"))
            .isEqualTo(OffsetTime.of(12, 30, 11, 0, ZoneOffset.UTC))
        assertThat(DateTimeConversionUtils.toOffsetTime("12:30:11.999"))
            .isEqualTo(OffsetTime.of(12, 30, 11, 999000000, ZoneOffset.UTC))
    }

    @Test
    fun offsetTimeStrict() {
        Assertions.assertThatThrownBy { DateTimeConversionUtils.toOffsetTime("12:30", true) }
            .isInstanceOf(DateTimeException::class.java)
        Assertions.assertThatThrownBy { DateTimeConversionUtils.toOffsetTime("12:30:11", true) }
            .isInstanceOf(DateTimeException::class.java)
        Assertions.assertThatThrownBy { DateTimeConversionUtils.toOffsetTime("12:30:11.999", true) }
            .isInstanceOf(DateTimeException::class.java)
        assertThat(DateTimeConversionUtils.toOffsetTime("12:30:11.999+01:00"))
            .isEqualTo(OffsetTime.of(12, 30, 11, 999000000, ZoneOffset.ofHoursMinutes(1, 0)))
    }

    @Test
    fun partialSimpleLocalTime() {
        assertThat(DateTimeConversionUtils.toLocalTime("1233+0100"))
            .isEqualTo(LocalTime.of(12, 33))
        assertThat(DateTimeConversionUtils.toLocalTime("07+0100"))
            .isEqualTo(LocalTime.of(7, 0))
        assertThat(DateTimeConversionUtils.toLocalTime("07"))
            .isEqualTo(LocalTime.of(7, 0))
    }

    @Test
    fun partialExtendedZonedDateTime() {
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33+01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12+01:00"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    @Test
    fun partialExtendedZonedDateTimeStrict() {
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33:00+01:00", true))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015-01-01T12:33+01:00", true))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        Assertions.assertThatThrownBy { DateTimeConversionUtils.toZonedDateTime("2015-01-01T12+01:00", true) }.isInstanceOf(
            DateTimeException::class.java)
        Assertions.assertThatThrownBy { DateTimeConversionUtils.toZonedDateTime("2015-01", true) }.isInstanceOf(
            DateTimeException::class.java)
    }

    @Test
    fun partialExtendedLocalDate() {
        assertThat(DateTimeConversionUtils.toLocalDate("2015-01"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("2015"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
    }

    @Test
    fun simpleZonedDateTime() {
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T123317+0100"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T1233+0100"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T1233+01"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T123317Z"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneId.of("Z")))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T123317"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T123317.00191+0100"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 1910000, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T123317,00191+0100"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 1910000, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T123317.00000017"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 17, 170, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T1233"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    @Test
    fun partialSimpleZonedDateTime() {
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T1233+0100"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T12+0100"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(1)))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T1233"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 33, 0, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("20150101T12"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("201501"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
        assertThat(DateTimeConversionUtils.toZonedDateTime("2015"))
            .isEqualTo(ZonedDateTime.of(2015, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    @Test
    fun simpleLocalDate() {
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T123317+0100"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T1233+0100"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T1233+01"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T123317Z"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T123317"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T123317.00191+0100"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T123317,00191+0100"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101T123317.00000017"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("20150101"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
    }

    @Test
    fun partialSimpleLocalDate() {
        assertThat(DateTimeConversionUtils.toLocalDate("201501"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
        assertThat(DateTimeConversionUtils.toLocalDate("2015"))
            .isEqualTo(LocalDate.of(2015, 1, 1))
    }

    @Test
    fun invalidDateTime() {
        assertThat(DateTimeConversionUtils.toOffsetDateTime("0001-01-01T00:57:44.000+00:57:44"))
            .isEqualTo(OffsetDateTime.of(1, 1, 1, 0, 57, 44, 0, ZoneOffset.ofHoursMinutesSeconds(0, 57, 44)))
    }

    @Test
    fun toJoda() {
        assertThat(
            DateTimeConversionUtils.toDateTime(OffsetDateTime.of(2016, 1, 1, 12, 30, 17, 100000, ZoneOffset.ofHours(2)))
                .compareTo(DateTime(2016, 1, 1, 12, 30, 17, 0, DateTimeZone.forOffsetHours(2)))).isEqualTo(0)
        assertThat(
            DateTimeConversionUtils.toDateTime(OffsetDateTime.of(2016, 1, 1, 12, 30, 17, 100000000, ZoneOffset.ofHours(2)))
                .compareTo(DateTime(2016, 1, 1, 12, 30, 17, 100, DateTimeZone.forOffsetHours(2)))).isEqualTo(0)
    }

    @Test
    fun fromJoda() {
        assertThat(DateTimeConversionUtils.toOffsetDateTime(DateTime(2016, 1, 1, 12, 30, 17, 0, DateTimeZone.forOffsetHours(2))))
            .isEqualTo(OffsetDateTime.of(2016, 1, 1, 12, 30, 17, 0, ZoneOffset.ofHours(2)))
        assertThat(DateTimeConversionUtils.toOffsetDateTime(DateTime(2016, 1, 1, 12, 30, 17, 100, DateTimeZone.forOffsetHours(2))))
            .isEqualTo(OffsetDateTime.of(2016, 1, 1, 12, 30, 17, 100000000, ZoneOffset.ofHours(2)))
    }

    @Test
    fun negativeDateTime() {
        val dateTime: DateTime = DvDateTime.toDateTime("-0001-12-31T23:30:20.000+02:30:20")
        val offsetDateTime = DateTimeConversionUtils.toOffsetDateTime("-0001-12-31T23:30:20.000+02:30:20")
        assertThat(DateTimeConversionUtils.toDateTime(offsetDateTime)).isEqualByComparingTo(dateTime)
    }

    @Test
    fun offsetAndPeriod() {
        val now = OffsetDateTime.now()
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("P1Y"))).isEqualTo(now.plusYears(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("P1M"))).isEqualTo(now.plusMonths(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("P1W"))).isEqualTo(now.plusWeeks(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("P1D"))).isEqualTo(now.plusDays(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("PT1H"))).isEqualTo(now.plusHours(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("PT1M"))).isEqualTo(now.plusMinutes(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("PT1S"))).isEqualTo(now.plusSeconds(1L))
        assertThat(DateTimeConversionUtils.plusPeriod(now, Period.parse("P1Y1WT1S")))
            .isEqualTo(now.plusYears(1L).plusWeeks(1L).plusSeconds(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("P1Y"))).isEqualTo(now.minusYears(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("P1M"))).isEqualTo(now.minusMonths(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("P1W"))).isEqualTo(now.minusWeeks(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("P1D"))).isEqualTo(now.minusDays(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("PT1H"))).isEqualTo(now.minusHours(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("PT1M"))).isEqualTo(now.minusMinutes(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("PT1S"))).isEqualTo(now.minusSeconds(1L))
        assertThat(DateTimeConversionUtils.minusPeriod(now, Period.parse("P1Y1WT1S")))
            .isEqualTo(now.minusYears(1L).minusWeeks(1L).minusSeconds(1L))
    }
}
