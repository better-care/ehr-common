package care.better.openehr.archetypemodel

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class AmObject {
    companion object {
        const val AM_VERSION = "1.0.1"
    }
}