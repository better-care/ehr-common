package org.openehr.am.aom

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ExprLeaf : ExprItem() {
    @Contextual
    lateinit var item: Any
    lateinit var referenceType: String
}