package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ArchetypeInternalRef : CObject() {
    @RequiresNotNull
    var targetPath: String? = null
}