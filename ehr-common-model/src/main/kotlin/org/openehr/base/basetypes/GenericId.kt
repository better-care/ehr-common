package org.openehr.base.basetypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class GenericId : ObjectId() {
    lateinit var scheme: String
}