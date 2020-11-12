package org.openehr.rm.common

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ImportedVersion : Version() {
    @RequiresNotNull
    var item: OriginalVersion? = null
}