package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

abstract class DvTimeSpecification : DataValue() {
    lateinit var value: DvParsable
}