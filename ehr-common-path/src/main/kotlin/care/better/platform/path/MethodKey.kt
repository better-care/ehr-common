package care.better.platform.path

import java.util.Objects

/**
 * Holds information about method key.
 *
 * @constructor Creates a new instance of [MethodKey]
 * @param clazz [Class]
 * @param propertyName Name of the property
 */
data class MethodKey(val clazz: Class<*>, val propertyName: String) {
    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other == null || javaClass != other.javaClass -> false
            else -> clazz == (other as MethodKey).clazz && propertyName == other.propertyName
        }

    override fun hashCode(): Int = Objects.hash(clazz, propertyName)
}
