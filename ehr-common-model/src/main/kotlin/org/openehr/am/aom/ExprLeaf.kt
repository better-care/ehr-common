package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class ExprLeaf : ExprItem() {
    lateinit var item: Any
    lateinit var referenceType: String
}