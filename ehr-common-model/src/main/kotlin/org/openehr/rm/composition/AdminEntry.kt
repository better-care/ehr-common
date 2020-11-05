package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Serializable
class AdminEntry : Entry() {
    lateinit var data: ItemStructure
}