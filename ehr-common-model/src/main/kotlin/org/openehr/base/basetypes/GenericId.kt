package org.openehr.base.basetypes

/**
 * @author Primoz Delopst
 */

class GenericId : ObjectId() {
    lateinit var scheme: String
}