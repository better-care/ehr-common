package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvMultimedia : DvEncapsulated() {
    var alternateText: String? = null
    var uri: DvUri? = null
    var data: ByteArray? = null
    lateinit var mediaType: CodePhrase
    var compressionAlgorithm: CodePhrase? = null
    var integrityCheck: ByteArray? = null
    var integrityCheckAlgorithm: CodePhrase? = null
    var size: Int = 0
    var thumbnail: DvMultimedia? = null
}