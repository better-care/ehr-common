package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class Composition : Locatable() {
    @RequiresNotNull
    var language: CodePhrase? = null

    @RequiresNotNull
    var territory: CodePhrase? = null

    @RequiresNotNull
    var category: DvCodedText? = null

    @RequiresNotNull
    var composer: PartyProxy? = null
    var context: EventContext? = null
    var content: MutableList<ContentItem> = mutableListOf()
}