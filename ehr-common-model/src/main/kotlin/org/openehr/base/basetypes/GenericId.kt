package org.openehr.base.basetypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class GenericId : ObjectId() {
    @RequiresNotNull
    var scheme: String? = null
}