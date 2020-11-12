package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ConstraintRef : CObject() {
    @RequiresNotNull
    var reference: String? = null
}