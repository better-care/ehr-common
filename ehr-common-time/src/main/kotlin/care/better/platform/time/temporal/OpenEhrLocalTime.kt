package care.better.platform.time.temporal

import care.better.platform.time.OpenEhrDateTimeFormatterContext
import java.io.Serializable
import java.time.Clock
import java.time.LocalTime
import java.time.format.ResolverStyle
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalField
import java.time.temporal.TemporalUnit

/**
 * @author Matic Ribic
 */
class OpenEhrLocalTime(time: LocalTime, precisionField: OpenEhrField, fieldStates: Map<OpenEhrField, OpenEhrFieldState>, resolverStyle: ResolverStyle) :
    OpenEhrTemporal<LocalTime>(time, precisionField, fieldStates, resolverStyle), Comparable<OpenEhrLocalTime>, Serializable {

    companion object {

        @JvmStatic
        fun now(): OpenEhrLocalTime = now(Clock.systemDefaultZone())

        @JvmStatic
        fun now(clock: Clock): OpenEhrLocalTime =
            OpenEhrLocalTime(LocalTime.now(clock), OpenEhrField.MINIMUM, getDefaultFieldStates(OpenEhrField.TIME_FIELDS), ResolverStyle.SMART)

        @JvmStatic
        @JvmOverloads
        fun of(hour: Int, minute: Int? = null, second: Int? = null, nano: Int? = null): OpenEhrLocalTime =
            of(hour, minute, second, nano, getDefaultFieldStates(OpenEhrField.TIME_FIELDS))

        @JvmStatic
        fun of(
                hour: Int,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null,
                fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
                resolverStyle: ResolverStyle = ResolverStyle.SMART): OpenEhrLocalTime =
            OpenEhrLocalTime(
                    LocalTime.of(hour, minute ?: 0, second ?: 0, nano ?: 0),
                    getPrecision(hour, minute, second, nano)!!,
                    fieldStates,
                    resolverStyle)

        @JvmStatic
        fun getPrecision(hour: Int?, minute: Int?, second: Int?, nano: Int?): OpenEhrField? =
            when {
                hour == null -> null
                minute == null -> OpenEhrField.HOURS
                second == null -> OpenEhrField.MINUTES
                nano == null -> OpenEhrField.SECONDS
                else -> OpenEhrField.NANOS
            }

        @JvmStatic
        fun from(temporal: TemporalAccessor, precisionField: OpenEhrField, context: OpenEhrDateTimeFormatterContext): OpenEhrLocalTime {
            if (temporal is OpenEhrLocalTime) {
                return temporal
            }

            return OpenEhrLocalTime(LocalTime.from(temporal), precisionField, context.patternFieldStates, context.resolverStyle)
        }
    }

    val time: LocalTime = temporal

    override fun with(field: TemporalField, newValue: Long): Temporal =
        OpenEhrLocalTime(time.with(field, newValue), withTemporalField(field), fieldStates, resolverStyle)

    override fun plus(amountToAdd: Long, unit: TemporalUnit): Temporal =
        OpenEhrLocalTime(time.plus(amountToAdd, unit), withTemporalUnit(unit), fieldStates, resolverStyle)

    override fun toExactTemporal(): TemporalAccessor = this.takeUnless { precisionField == OpenEhrField.NANOS } ?: time

    override fun compareTo(other: OpenEhrLocalTime): Int = time.compareTo(other.time)
}