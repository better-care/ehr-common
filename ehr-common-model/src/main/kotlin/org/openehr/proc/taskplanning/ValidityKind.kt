package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
enum class ValidityKind : EnumerationInteger, EnumerationString {
    MANDATORY,
    OPTIONAL,
    PROHIBITED;
}