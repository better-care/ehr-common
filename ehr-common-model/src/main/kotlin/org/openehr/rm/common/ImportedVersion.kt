package org.openehr.rm.common

/**
 * @author Primoz Delopst
 */

class ImportedVersion : Version() {
    lateinit var item: OriginalVersion
}