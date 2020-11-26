package care.better.platform.json.jackson.mixedin

import com.fasterxml.jackson.annotation.JsonIgnore
import org.openehr.proc.taskplanning.TypeDefBoolean

/**
 * @author Primoz Delopst
 */

abstract class BooleanContextExpressionMixedIn{

    @JsonIgnore
    abstract fun setType(type: TypeDefBoolean)
}