package care.better.platform.time.temporal

import care.better.platform.time.OpenEhrDateTimeFormatterContext
import java.io.Serializable
import java.time.*
import java.time.format.ResolverStyle
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalField
import java.time.temporal.TemporalUnit

/**
 * @author Matic Ribic
 */
class OpenEhrOffsetTime(time: OffsetTime, precisionField: OpenEhrField, fieldStates: Map<OpenEhrField, OpenEhrFieldState>, resolverStyle: ResolverStyle) :
    OpenEhrTemporal<OffsetTime>(time, precisionField, fieldStates, resolverStyle), Comparable<OpenEhrOffsetTime>, Serializable {

    companion object {

        @JvmStatic
        fun now(): OpenEhrOffsetTime = now(Clock.systemDefaultZone())

        @JvmStatic
        fun now(clock: Clock): OpenEhrOffsetTime =
            OpenEhrOffsetTime(OffsetTime.now(clock), OpenEhrField.MINIMUM, getDefaultFieldStates(OpenEhrField.TIME_FIELDS), ResolverStyle.SMART)

        @JvmStatic
        @JvmOverloads
        fun of(
                hour: Int,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null,
                offset: ZoneOffset): OpenEhrOffsetTime = of(hour, minute, second, nano, offset, getDefaultFieldStates(OpenEhrField.TIME_FIELDS))

        @JvmStatic
        fun of(
                hour: Int,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null,
                offset: ZoneOffset,
                fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
                resolverStyle: ResolverStyle = ResolverStyle.SMART): OpenEhrOffsetTime =
            OpenEhrOffsetTime(
                    OffsetTime.of(hour, minute ?: 0, second ?: 0, nano ?: 0, offset),
                    OpenEhrLocalTime.getPrecision(hour, minute, second, nano)!!,
                    fieldStates,
                    resolverStyle)

        @JvmStatic
        fun from(temporal: TemporalAccessor, precisionField: OpenEhrField, context: OpenEhrDateTimeFormatterContext): OpenEhrOffsetTime {
            if (temporal is OpenEhrOffsetTime) {
                return temporal
            }

            if (context.resolverStyle == ResolverStyle.STRICT) {
                arrayOf<(TemporalAccessor) -> OffsetTime>({ OffsetTime.from(it) })
            } else {
                arrayOf<(TemporalAccessor) -> OffsetTime>(
                        { OffsetTime.from(it) },
                        { OffsetDateTime.from(temporal).toOffsetTime() },
                        { ZonedDateTime.from(temporal).toOffsetDateTime().toOffsetTime() })
            }.forEach {
                try {
                    return OpenEhrOffsetTime(it.invoke(temporal), precisionField, context.patternFieldStates, context.resolverStyle)
                } catch (e: Exception) {
                }
            }

            throw DateTimeException("Invalid time $temporal")
        }
    }

    val time: OffsetTime = temporal

    override fun with(field: TemporalField, newValue: Long): Temporal =
        OpenEhrOffsetTime(time.with(field, newValue), withTemporalField(field), fieldStates, resolverStyle)

    override fun plus(amountToAdd: Long, unit: TemporalUnit): Temporal =
        OpenEhrOffsetTime(time.plus(amountToAdd, unit), withTemporalUnit(unit), fieldStates, resolverStyle)

    override fun toExactTemporal(): TemporalAccessor = this.takeUnless { precisionField == OpenEhrField.NANOS } ?: time

    override fun compareTo(other: OpenEhrOffsetTime): Int = time.compareTo(other.time)
}