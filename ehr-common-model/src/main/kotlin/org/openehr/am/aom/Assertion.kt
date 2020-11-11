package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Assertion : AmObject(), Serializable {
    var tag: String? = null
    var stringExpression: String? = null
    lateinit var expression: ExprItem
    var variables: MutableList<AssertionVariable> = mutableListOf()
}