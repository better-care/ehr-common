package org.openehr.rm.composition

import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class Evaluation : CareEntry() {
    lateinit var data: ItemStructure
}