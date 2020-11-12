package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ExprLeaf : ExprItem() {
    @RequiresNotNull
    var item: Any? = null

    @RequiresNotNull
    var referenceType: String? = null
}