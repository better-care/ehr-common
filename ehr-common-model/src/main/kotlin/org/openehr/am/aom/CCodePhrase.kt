package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

@Serializable
open class CCodePhrase : CDomainType() {
    var assumedValue: CodePhrase? = null
    var defaultValue: CodePhrase? = null
    var terminologyId: TerminologyId? = null
    var codeList: MutableList<String> = mutableListOf()
}