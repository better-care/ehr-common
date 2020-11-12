package care.better.platform.utils

import org.openehr.rm.datatypes.DvDate
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvTime
import java.time.*
import java.time.format.DateTimeFormatter

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 */

@Suppress("unused", "MemberVisibilityCanBePrivate")
class JSR310ConversionUtils {

    companion object {
        /**
         * Converts [ZonedDateTime] to [DvDateTime].
         * Please note that only the offset is preserved, the actual time-zone is discarded.
         *
         * @param dateTime [ZonedDateTime]
         * @return [DvDateTime]
         */
        fun toDvDateTime(dateTime: ZonedDateTime): DvDateTime =
                DvDateTime().apply {
                    this.value = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(dateTime)
                }

        /**
         * Converts [OffsetDateTime] to [DvDateTime]
         *
         * @param dateTime [OffsetDateTime]
         * @return [DvDateTime]
         */
        fun toDvDateTime(dateTime: OffsetDateTime): DvDateTime =
                DvDateTime().apply {
                    this.value = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(dateTime)
                }

        /**
         * Converts [LocalTime] to [DvDate]
         *
         * @param date [LocalTime]
         * @return [DvDate]
         */
        fun toDvDate(date: LocalDate): DvDate =
                DvDate().apply {
                    this.value = DateTimeFormatter.ISO_LOCAL_DATE.format(date)
                }

        /**
         * Converts [LocalTime] to [DvTime]
         *
         * @param time [LocalTime]
         * @return [DvTime]
         */
        fun toDvTime(time: LocalTime): DvTime = DvTime().apply {
            this.value = DateTimeFormatter.ISO_LOCAL_TIME.format(time)
        }

        /**
         * Converts [OffsetTime] to [DvTime]
         *
         * @param time [OffsetTime]
         * @return [DvTime]
         */
        fun toDvTime(time: OffsetTime): DvTime =
                DvTime().apply {
                    this.value = DateTimeFormatter.ISO_OFFSET_TIME.format(time)
                }

        /**
         * Converts [DvDateTime] to [ZonedDateTime]
         *
         * @param dateTime [DvDateTime]
         * @return [ZonedDateTime]
         */
        fun toZonedDateTime(dateTime: DvDateTime): ZonedDateTime = DateTimeConversionUtils.toZonedDateTime(dateTime.value!!)

        /**
         * Converts [DvDateTime] to [OffsetDateTime]
         *
         * @param dateTime [DvDateTime]
         * @return [OffsetDateTime]
         */
        fun toOffsetDateTime(dateTime: DvDateTime): OffsetDateTime = toZonedDateTime(dateTime).toOffsetDateTime()

        /**
         * Converts [DvTime] to [LocalTime]
         *
         * @param time [DvTime]
         * @return [LocalTime]
         */
        fun toLocalTime(time: DvTime): LocalTime = DateTimeConversionUtils.toLocalTime(time.value!!)

        /**
         * Converts [DvTime] to [OffsetTime]
         *
         * @param time [DvTime]
         * @return [OffsetTime]
         */
        fun toOffsetTime(time: DvTime): OffsetTime = DateTimeConversionUtils.toOffsetTime(time.value!!)

        /**
         * Converts [DvDate] to [LocalDate]
         *
         * @param date [DvDate]
         * @return [DvDateTime]
         */
        fun toLocalDate(date: DvDate): LocalDate = DateTimeConversionUtils.toLocalDate(date.value!!)
    }
}