package care.better.platform.time.temporal

/**
 * @author Matic Ribic
 */
enum class OpenEhrFieldState(val defined: Boolean, val possible: Boolean) {
    MANDATORY(true, true), OPTIONAL(true, true), UNDEFINED(false, true), FORBIDDEN(true, false)
}