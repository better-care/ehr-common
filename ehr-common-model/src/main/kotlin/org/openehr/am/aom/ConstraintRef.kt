package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ConstraintRef : CObject() {
    lateinit var reference: String
}