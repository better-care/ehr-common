package care.better.openehr.rm

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class RmObject {
    companion object {
        val RM_VERSION = RmVersion.RM1_0_4
    }
}