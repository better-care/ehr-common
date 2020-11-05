package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class State : AmObject() {
    lateinit var name: String
}