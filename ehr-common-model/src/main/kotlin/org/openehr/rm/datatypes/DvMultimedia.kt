package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvMultimedia : DvEncapsulated() {
    var alternateText: String? = null
    var uri: DvUri? = null
    var data: ByteArray? = null

    @RequiresNotNull
    var mediaType: CodePhrase? = null
    var compressionAlgorithm: CodePhrase? = null
    var integrityCheck: ByteArray? = null
    var integrityCheckAlgorithm: CodePhrase? = null
    var size: Int = 0
    var thumbnail: DvMultimedia? = null
}