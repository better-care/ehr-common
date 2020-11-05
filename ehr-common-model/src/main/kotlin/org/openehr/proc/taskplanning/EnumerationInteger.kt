package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

interface EnumerationInteger {
    fun integerValue(): Int =
            if (this is Enum<*>)
                (this as Enum<*>).ordinal
            else
                throw AssertionError("EnumerationInteger " + javaClass.simpleName + " is not an Enum")


    fun <T : EnumerationInteger> fromInteger(enumClass: Class<T>, value: Int): T =
            enumClass.enumConstants.firstOrNull { it.integerValue() == value }
                    ?: throw IllegalArgumentException("No such " + enumClass.simpleName + ": " + value)
}