/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package care.better.openehr.terminology

import care.better.platform.utils.XmlUtils
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class OpenEhrTerminology(private val groups: Map<String, TermGroup>, private val terms: Map<TermKey, String>) {

    companion object {
        private const val DEFAULT_LANGUAGE: String = "en"
        private val NORMAL_STATUS_CODES: Set<String> = setOf("HHH", "HH", "H", "N", "L", "LL", "LLL")
        private val MAGNITUDE_STATUS_CODES: Set<String> = setOf("=", "<", ">", "<=", ">=", "~")


        private val instance = with(XmlUtils.createSAXParserFactory()) {
            val xmlReader = this.newSAXParser().xmlReader
            val terminologyHandler = TerminologyHandler()
            xmlReader.contentHandler = terminologyHandler
            xmlReader.parse(InputSource(OpenEhrTerminology::class.java.getResourceAsStream("/care/better/openehr/terminology/openehr_external_terminologies.xml")))
            xmlReader.parse(InputSource(OpenEhrTerminology::class.java.getResourceAsStream("/care/better/openehr/terminology/en/openehr_terminology.xml")))
            xmlReader.parse(InputSource(OpenEhrTerminology::class.java.getResourceAsStream("/care/better/openehr/terminology/ja/openehr_terminology.xml")))
            xmlReader.parse(InputSource(OpenEhrTerminology::class.java.getResourceAsStream("/care/better/openehr/terminology/pt/openehr_terminology.xml")))
            OpenEhrTerminology(terminologyHandler.getGroups(), terminologyHandler.getTerms())
        }

        /**
         * Returns singleton instance of [OpenEhrTerminology]
         *
         * @return [OpenEhrTerminology]
         */
        @JvmStatic
        fun getInstance(): OpenEhrTerminology = instance

        /**
         * Gets a set of normal status codes (HHH, HH, H, N, L, LL, LLL)
         *
         * @return set of codes
         */
        @JvmStatic
        fun getNormalStatusCodes(): Set<String> = NORMAL_STATUS_CODES

        /**
         * Gets a set of magnitude status codes (=, &lt;, &gt;, &lt;=, &gt;=, ~)
         *
         * @return set of codes
         */
        @JvmStatic
        fun getMagnitudeStatusCodes(): Set<String?> = MAGNITUDE_STATUS_CODES

    }

    /**
     * Get term value for the specified language and code
     *
     * @param language language
     * @param code     code
     * @return term value
     */
    fun getText(language: String, code: String): String? = terms[TermKey(language, code)]

    /**
     * Get group term value for the specified language and group code
     *
     * @param language  language
     * @param groupName group name
     * @return group term value
     */
    fun getGroupTerm(language: String, groupName: String): String? = groups[groupName]?.let { terms[TermKey(language, it.name)] }


    /**
     * Get group children codes for the specified group code
     *
     * @param groupName group code
     * @return collection of children codes
     */
    fun getGroupChildren(groupName: String?): Collection<String> =
        with(groups[groupName]) {
            this?.termCodes?.toList() ?: emptyList()
        }

    /**
     * Gets id of the child in specified group by specified name
     *
     * @param groupName group name
     * @param name    name
     * @return child id (or null if not found)
     */
    fun getId(groupName: String, name: String?): String? {
        if (name != null) {
            for (id in getGroupChildren(groupName)) {
                if (name.equals(getText(DEFAULT_LANGUAGE, id), ignoreCase = true)) {
                    return id
                }
            }
        }
        return null
    }

    private class TerminologyHandler : DefaultHandler() {
        private val terms: MutableMap<TermKey, String> = mutableMapOf()
        private val groups: MutableMap<String, TermGroup> = mutableMapOf()
        var currentParent: Any? = null
        var currentLanguage: String? = null

        override fun startElement(uri: String?, localName: String?, qName: String, attributes: Attributes) {
            when (qName) {
                "group" -> {
                    val name = attributes.getValue("name")
                    val termGroup = getTermGroup(name)
                    termGroup.name = name
                    currentParent = termGroup
                }
                "concept" -> {
                    if (currentParent is TermGroup) {
                        val id = attributes.getValue("id")
                        terms[TermKey(currentLanguage!!, id)] = attributes.getValue("rubric")
                        (currentParent as TermGroup).termCodes.add(id)
                    }
                }
                "terminology" -> currentLanguage = attributes.getValue("language")
            }
        }

        override fun endElement(uri: String?, localName: String?, qName: String?) {
            when (qName) {
                "group" -> currentParent = null
                "terminology" -> currentLanguage = null
            }
        }

        private fun getTermGroup(name: String): TermGroup = groups.computeIfAbsent(name) { TermGroup() }
        fun getTerms(): Map<TermKey, String> = terms.toMap()
        fun getGroups(): Map<String, TermGroup> = groups.toMap()
    }
}
