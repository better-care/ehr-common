package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TConstraints : AmObject(), Serializable{
    var attributes: MutableList<TAttribute> = mutableListOf()
}