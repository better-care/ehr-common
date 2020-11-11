package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvCodedText : DvText() {
    lateinit var definingCode: CodePhrase
}