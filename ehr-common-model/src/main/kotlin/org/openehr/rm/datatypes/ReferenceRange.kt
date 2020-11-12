package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ReferenceRange : RmObject(), Serializable {
    @RequiresNotNull
    var meaning: DvText? = null

    @RequiresNotNull
    var range: DvInterval? = null
}