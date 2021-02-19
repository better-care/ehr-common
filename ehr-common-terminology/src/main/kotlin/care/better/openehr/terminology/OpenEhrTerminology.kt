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

import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParserFactory

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class OpenEhrTerminology(private val groups: Map<String, TermGroup>, private val terms: Map<TermKey, String>) {

    companion object {
        private const val DEFAULT_LANGUAGE: String = "en"
        private val NORMAL_STATUS_CODES: Set<String> = setOf("HHH", "HH", "H", "N", "L", "LL", "LLL")
        private val MAGNITUDE_STATUS_CODES: Set<String> = setOf("=", "<", ">", "<=", ">=", "~")


        private val instance = with(SAXParserFactory.newInstance()) {
            this.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
            val xmlReader = this.newSAXParser().xmlReader

            val handler = TerminologyHandler()
            xmlReader.contentHandler = handler
            xmlReader.parse(InputSource(OpenEhrTerminology::class.java.getResource("/care/better/openehr/terminology/openehr-terminology.xml").toExternalForm()))

            OpenEhrTerminology(handler.getGroups(), handler.getTerms())
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
     * @param groupCode group code
     * @return group term value
     */
    fun getGroupTerm(language: String, groupCode: String): String? = groups[groupCode]?.let { terms[TermKey(language, it.groupTermCode)] }


    /**
     * Get group children codes for the specified group code
     *
     * @param groupCode group code
     * @return collection of children codes
     */
    fun getGroupChildren(groupCode: String?): Collection<String> =
        with(groups[groupCode]) {
            this?.termCodes?.toList() ?: emptyList()
        }

    /**
     * Gets id of the child in specified group by specified name
     *
     * @param groupId group id
     * @param name    name
     * @return child id (or null if not found)
     */
    fun getId(groupId: String, name: String?): String? {
        if (name != null) {
            for (id in getGroupChildren(groupId)) {
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

        override fun startElement(uri: String?, localName: String?, qName: String, attributes: Attributes) {
            when (qName) {
                "Concept" -> terms[TermKey(attributes.getValue("Language"), attributes.getValue("ConceptID"))] = attributes.getValue("Rubric")
                "Grouper" -> getTermGroup(attributes.getValue("id")).groupTermCode = attributes.getValue("ConceptID")
                "GroupedConcept" -> getTermGroup(attributes.getValue("GrouperID")).termCodes.add(attributes.getValue("ChildID"))
            }
        }

        private fun getTermGroup(id: String): TermGroup = groups.computeIfAbsent(id) { TermGroup() }

        fun getGroups(): Map<String, TermGroup> = groups.toMap()

        fun getTerms(): Map<TermKey, String> = terms.toMap()
    }
}
