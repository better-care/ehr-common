/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.rm.datatypes

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DV_MULTIMEDIA", propOrder = [
        "alternateText",
        "uri",
        "data",
        "mediaType",
        "compressionAlgorithm",
        "integrityCheck",
        "integrityCheckAlgorithm",
        "size",
        "thumbnail"])
@Open
class DvMultimedia() : DvEncapsulated() {
    private constructor(
            alternateText: String? = null,
            mediaType: CodePhrase? = null,
            compressionAlgorithm: CodePhrase? = null,
            integrityCheck: ByteArray? = null,
            integrityCheckAlgorithm: CodePhrase? = null,
            size: Int = 0,
            thumbnail: DvMultimedia? = null,
            charset: CodePhrase? = null,
            language: CodePhrase? = null) : this() {
        this.alternateText = alternateText
        this.mediaType = mediaType
        this.compressionAlgorithm = compressionAlgorithm
        this.integrityCheck = integrityCheck
        this.integrityCheckAlgorithm = integrityCheckAlgorithm
        this.size = size
        this.thumbnail = thumbnail
        this.charset = charset
        this.language = language
    }

    @JvmOverloads
    constructor(
            uri: DvUri,
            data: ByteArray? = null,
            alternateText: String? = null,
            mediaType: CodePhrase? = null,
            compressionAlgorithm: CodePhrase? = null,
            integrityCheck: ByteArray? = null,
            integrityCheckAlgorithm: CodePhrase? = null,
            size: Int = 0,
            thumbnail: DvMultimedia? = null,
            charset: CodePhrase? = null,
            language: CodePhrase? = null
    ) : this(alternateText, mediaType, compressionAlgorithm, integrityCheck, integrityCheckAlgorithm, size, thumbnail, charset, language) {
        this.uri = uri
        this.data = data
    }

    @JvmOverloads
    constructor(
            data: ByteArray,
            uri: DvUri? = null,
            alternateText: String? = null,
            mediaType: CodePhrase? = null,
            compressionAlgorithm: CodePhrase? = null,
            integrityCheck: ByteArray? = null,
            integrityCheckAlgorithm: CodePhrase? = null,
            size: Int = 0,
            thumbnail: DvMultimedia? = null,
            charset: CodePhrase? = null,
            language: CodePhrase? = null
    ) : this(alternateText, mediaType, compressionAlgorithm, integrityCheck, integrityCheckAlgorithm, size, thumbnail, charset, language) {
        this.uri = uri
        this.data = data
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "alternate_text")
    var alternateText: String? = null
    var uri: DvUri? = null
    var data: ByteArray? = null

    @XmlElement(name = "media_type", required = true)
    @Required
    var mediaType: CodePhrase? = null

    @XmlElement(name = "compression_algorithm")
    var compressionAlgorithm: CodePhrase? = null

    @XmlElement(name = "integrity_check")
    var integrityCheck: ByteArray? = null

    @XmlElement(name = "integrity_check_algorithm")
    var integrityCheckAlgorithm: CodePhrase? = null
    var size: Int = 0
    var thumbnail: DvMultimedia? = null
}
