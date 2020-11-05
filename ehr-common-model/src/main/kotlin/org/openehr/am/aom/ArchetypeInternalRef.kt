package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ArchetypeInternalRef : CObject() {
    lateinit var targetPath: String
}