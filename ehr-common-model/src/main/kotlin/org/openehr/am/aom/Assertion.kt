package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Assertion : AmObject(), Serializable {
    var tag: String? = null
    var stringExpression: String? = null

    @RequiresNotNull
    var expression: ExprItem? = null
    var variables: MutableList<AssertionVariable> = mutableListOf()
}