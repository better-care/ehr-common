package care.better.openehr.processmodel.taskplanning

/**
 * @author Primoz Delopst
 */

enum class TpVersion(val version: String, private val possibleValues: Set<String>) {

    TP1_5_0("tp1.5.0", setOf("tp1.5.0", "TP1.5.0", "tp1_5_0", "TP1_5_0")),
    TP1_5_1("tp1.5.1", setOf("tp1.5.1", "TP1.5.1", "tp1_5_1", "TP1_5_1"));

    companion object {
        fun from(version: String): TpVersion? =
                when {
                    TP1_5_0.possibleValues.contains(version) -> TP1_5_0
                    TP1_5_1.possibleValues.contains(version) -> TP1_5_1
                    else -> null
                }
    }
}