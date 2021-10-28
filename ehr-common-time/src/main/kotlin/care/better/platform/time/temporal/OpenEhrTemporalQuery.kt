package care.better.platform.time.temporal

import care.better.platform.time.OpenEhrDateTimeFormatterContext
import java.time.temporal.TemporalAccessor

/**
 * @author Matic Ribic
 */
fun interface OpenEhrTemporalQuery<T> {

    fun queryFrom(temporal: TemporalAccessor, precisionField: OpenEhrField, context: OpenEhrDateTimeFormatterContext): T
}