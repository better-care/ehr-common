package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
enum class OverrideType : EnumerationInteger, EnumerationString{
    ALLOWED,
    ALLOWED_WITH_REASON,
    PROHIBITED;
}