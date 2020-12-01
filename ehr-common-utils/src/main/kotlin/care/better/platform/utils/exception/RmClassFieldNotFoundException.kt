package care.better.platform.utils.exception

/**
 * @author Primoz Delopst
 */
class RmClassFieldNotFoundException(className: String, fieldName: String) : RuntimeException("Field $fieldName not found for class $className.")