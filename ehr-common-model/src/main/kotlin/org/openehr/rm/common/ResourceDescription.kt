package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ResourceDescription : RmObject(), Serializable {
    var originalAuthor: MutableList<StringDictionaryItem> = mutableListOf()
    var otherContributors: MutableList<String> = mutableListOf()
    lateinit var lifecycleState: String
    var resourcePackageUri: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
    var details: MutableList<ResourceDescriptionItem> = mutableListOf()
    var parentResource: AuthoredResource? = null
}