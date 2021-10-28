package care.better.platform.time.temporal

/**
 * @author Matic Ribic
 */
abstract class OpenEhrFieldStateHolder(protected val fieldStates: Map<OpenEhrField, OpenEhrFieldState>) {

    fun isFieldMandatory(field: OpenEhrField) = fieldStates[field] == OpenEhrFieldState.MANDATORY

    fun isFieldPossible(field: OpenEhrField) = fieldStates[field]?.possible == true
}