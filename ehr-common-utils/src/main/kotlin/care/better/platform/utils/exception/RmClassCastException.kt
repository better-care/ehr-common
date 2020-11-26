package care.better.platform.utils.exception

/**
 * @author Primoz Delopst
 */
class RmClassCastException(className: String) : RuntimeException("Class $className is not an instance of RM class.")
