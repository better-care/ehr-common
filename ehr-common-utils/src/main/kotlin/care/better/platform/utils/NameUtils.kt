package care.better.platform.utils

import org.openehr.rm.common.Locatable

@Suppress("unused")
class NameUtils {
    companion object {
        private val NAME_WITH_INDEX = "\\s*#[0-9]+$".toRegex()

        @JvmStatic
        fun nameMatches(locatable: Locatable, constrainedNames: List<String>, relaxedNameMatching: Boolean): Boolean {
            val locatableName = locatable.name?.value
            return locatableName?.let { nameMatches(it, constrainedNames, relaxedNameMatching) } ?: false
        }

        @JvmStatic
        fun nameMatches(locatableName: String, constrainedNames: List<String>, relaxedNameMatching: Boolean): Boolean {
            if (constrainedNames.contains(locatableName)) {
                return true
            }
            val nameForMatching = if (relaxedNameMatching) {
                NAME_WITH_INDEX.replace(locatableName, "")
            } else {
                locatableName
            }
            return constrainedNames.contains(nameForMatching)
        }
    }
}