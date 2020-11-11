package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotEmpty
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ResourceDescription : RmObject(), Serializable {
    @RequiresNotEmpty
    var originalAuthor: MutableList<StringDictionaryItem> = mutableListOf()
    var otherContributors: MutableList<String> = mutableListOf()
    lateinit var lifecycleState: String
    var resourcePackageUri: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
    @RequiresNotEmpty
    var details: MutableList<ResourceDescriptionItem> = mutableListOf()
    var parentResource: AuthoredResource? = null
}