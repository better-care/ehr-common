package org.openehr.rm.common

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ImportedVersion : Version() {
    lateinit var item: OriginalVersion
}