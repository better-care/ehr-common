package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull


/**
 * @author Primoz Delopst
 */

class CCodeReference : CCodePhrase() {
    @RequiresNotNull
    var referenceSetUri: String? = null
}