package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvCodedText : DvText() {
    @RequiresNotNull
    var definingCode: CodePhrase? = null
}