package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class ExprItem : AmObject() {
    lateinit var type: String
}