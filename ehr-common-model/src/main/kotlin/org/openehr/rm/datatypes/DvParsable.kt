package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvParsable : DvEncapsulated() {
    lateinit var value: String
    lateinit var formalism: String
}