package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class Assertion : AmObject() {
    var tag: String? = null
    var stringExpression: String? = null
    lateinit var expression: ExprItem
    var variables: MutableList<AssertionVariable> = mutableListOf()
}