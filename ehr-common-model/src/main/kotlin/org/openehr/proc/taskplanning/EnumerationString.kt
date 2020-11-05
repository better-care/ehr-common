package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
interface EnumerationString {
    fun stringValue(): String =
            if (this is Enum<*>)
                (this as Enum<*>).name
            else
                throw AssertionError("EnumerationString " + javaClass.simpleName + " is not an Enum")


    fun <T : EnumerationString> fromString(enumClass: Class<T>, value: String): T =
            enumClass.enumConstants.firstOrNull { it.stringValue().equals(value, ignoreCase = true) }
                    ?: throw IllegalArgumentException("No such " + enumClass.simpleName + ": " + value)
}