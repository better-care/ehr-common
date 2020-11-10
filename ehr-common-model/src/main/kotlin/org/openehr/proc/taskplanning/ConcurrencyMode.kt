package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
enum class ConcurrencyMode : EnumerationString, EnumerationInteger {
    XOR_ONE_PATH,
    AND_ALL_PATHS,
    OR_FIRST_COMPLETED,
    OR_ALL_STARTED;
}