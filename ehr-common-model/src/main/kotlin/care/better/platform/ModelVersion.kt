package care.better.platform

import care.better.openehr.processmodel.taskplanning.TpVersion
import care.better.openehr.rm.RmVersion

/**
 * @author Primoz Delopst
 */

class ModelVersion(val rmVersion: RmVersion, val tpVersion: TpVersion?) {

    constructor(rmVersion: RmVersion) : this(rmVersion, null)

    companion object {
        @JvmField
        val CURRENT_VERSION = ModelVersion(RmVersion.RM1_0_4, TpVersion.TP1_5_1)

        @JvmField
        val LEGACY_VERSION = ModelVersion(RmVersion.RM1_0_2)

        @JvmField
        val CURRENT_VERSION_STRING = CURRENT_VERSION.toString()

        @JvmStatic
        fun from(versionString: String): ModelVersion = with(versionString.split(",")) {
            if (this.size > 1) {
                ModelVersion(RmVersion.from(this[0]), TpVersion.from(this[1]))
            } else {
                ModelVersion(RmVersion.from(this[0]))
            }
        }
    }

    override fun toString(): String = "${rmVersion.version}${tpVersion?.let { ",${tpVersion.version}" } ?: ""}"
}