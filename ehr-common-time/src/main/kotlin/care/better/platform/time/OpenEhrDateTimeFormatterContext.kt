package care.better.platform.time

import care.better.platform.time.temporal.OpenEhrField
import care.better.platform.time.temporal.OpenEhrFieldState
import care.better.platform.time.temporal.OpenEhrFieldStateHolder
import java.time.format.ResolverStyle
import java.util.*

/**
 * @author Matic Ribic
 */
class OpenEhrDateTimeFormatterContext(
        val patternFieldStates: Map<OpenEhrField, OpenEhrFieldState>,
        val pattern: String?,
        val resolverStyle: ResolverStyle,
        val compact: Boolean,
        val locale: Locale): OpenEhrFieldStateHolder(patternFieldStates) {

    val patternDateTimeFieldStates = patternFieldStates.filter { it.key != OpenEhrField.OFFSET_SECONDS }
    val strict = resolverStyle == ResolverStyle.STRICT
    val undefinedPattern = pattern.isNullOrBlank()

    fun isPartialPattern(): Boolean =
        patternFieldStates.entries.asSequence().filter { it.key.ordinal >= OpenEhrField.SECONDS.ordinal }.map { it.value }
            .any { it == OpenEhrFieldState.OPTIONAL || it == OpenEhrFieldState.FORBIDDEN }

    fun filterFields(includeDate: Boolean, includeTime: Boolean): OpenEhrDateTimeFormatterContext =
        OpenEhrDateTimeFormatterContext(
                patternFieldStates.filter { it.key >= OpenEhrField.DAYS && includeDate || it.key <= OpenEhrField.HOURS && includeTime },
                pattern,
                resolverStyle,
                compact,
                locale)
}