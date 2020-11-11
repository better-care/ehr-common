package org.openehr.rm.composition

import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class Composition : Locatable() {
    lateinit var language: CodePhrase
    lateinit var territory: CodePhrase
    lateinit var category: DvCodedText
    lateinit var composer: PartyProxy
    var context: EventContext? = null
    var content: MutableList<ContentItem> = mutableListOf()
}