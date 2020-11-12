package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Link : RmObject(), Serializable {
    @RequiresNotNull
    var meaning: DvText? = null

    @RequiresNotNull
    var type: DvText? = null

    @RequiresNotNull
    var target: DvEhrUri? = null
}