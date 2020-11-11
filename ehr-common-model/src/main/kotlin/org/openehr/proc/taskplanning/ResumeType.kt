package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
enum class ResumeType : EnumerationInteger, EnumerationString {
    RESUME_SPECIFIED,
    RETRY_CURRENT_GROUP,
    NEW_THREAD;
}