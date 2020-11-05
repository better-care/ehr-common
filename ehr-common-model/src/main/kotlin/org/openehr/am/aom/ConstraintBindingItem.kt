package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ConstraintBindingItem : AmObject() {
    lateinit var value: String
    lateinit var code: String
}