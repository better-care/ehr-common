package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Serializable
class Evaluation : CareEntry() {
    lateinit var data: ItemStructure
}