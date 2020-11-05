package org.openehr.base.basetypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class LocatableRef : ObjectRef() {
    var path: String? = null
}