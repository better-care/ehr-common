package org.openehr.rm.composition

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class Section : ContentItem() {
    var items: List<ContentItem> = mutableListOf()
}