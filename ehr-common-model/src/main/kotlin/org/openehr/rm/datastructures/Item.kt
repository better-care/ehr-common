package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class Item : Locatable()