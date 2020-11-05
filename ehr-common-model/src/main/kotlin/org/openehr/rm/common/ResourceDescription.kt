package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ResourceDescription : RmObject() {
    var originalAuthor: MutableList<StringDictionaryItem> = mutableListOf()
    var otherContributors: MutableList<String> = mutableListOf()
    lateinit var lifecycleState: String
    var resourcePackageUri: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
    var details: MutableList<ResourceDescriptionItem> = mutableListOf()
    var parentResource: AuthoredResource? = null
}