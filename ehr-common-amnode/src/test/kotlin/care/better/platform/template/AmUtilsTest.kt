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

package care.better.platform.template

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.rm.common.Archetyped
import org.openehr.rm.common.Locatable
import org.openehr.rm.composition.Observation
import org.openehr.rm.datastructures.Element
import org.openehr.rm.datatypes.DvCodedText.Companion.create
import org.openehr.rm.datatypes.DvText
import java.io.IOException

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
class AmUtilsTest : AbstractAmTest() {
    @Test
    @Throws(IOException::class)
    fun testFindMatch() {
        val template = loadTemplate("/openEHR-EHR-COMPOSITION.t_ophthalmologist_examination.v1.opt")
        val builder = AmTreeBuilder(template)
        val root = builder.build()
        val node = AmUtils.resolvePath(root, "/content[openEHR-EHR-SECTION.eye_exam.v1,'Офтальмологический осмотр']")
        val locatable: Locatable = Observation()
        val archetyped = Archetyped()
        val archetypeId = ArchetypeId()
        archetypeId.value = "openEHR-EHR-OBSERVATION.eye_exam.v1"
        archetyped.archetypeId = archetypeId
        locatable.archetypeDetails = archetyped
        locatable.name = DvText("Светоощущение")
        locatable.archetypeNodeId = "at0000"
        val match = AmUtils.findMatchingNode(node?.attributes?.get("items")?.children ?: emptyList(), locatable)
        assertThat(AmUtils.isNameConstrained(match!!)).isTrue
    }

    @Test
    @Throws(IOException::class)
    fun testConstrainedNameCheck() {
        val template = loadTemplate("/Blood pressure.opt")
        val builder = AmTreeBuilder(template)
        val root = builder.build()
        val firstNode = AmUtils.resolvePath(root, "/content[[openEHR-EHR-OBSERVATION.blood_pressure.v1]")
        assertThat(AmUtils.isNameConstrained(firstNode!!)).isTrue
        val secondNode = AmUtils.resolvePath(root, "/content[[openEHR-EHR-OBSERVATION.blood_pressure.v1]/data[at0001]/events[at0006]")
        assertThat(AmUtils.isNameConstrained(secondNode!!)).isFalse
    }

    @Test
    @Throws(IOException::class)
    fun testConstrainedCodedTextNameCheck() {
        val template = loadTemplate("/Testing Template1.opt")
        val builder = AmTreeBuilder(template)
        val root = builder.build()
        val firstNode = AmUtils.resolvePath(root, "/context/other_context[at0001]/items[openEHR-EHR-CLUSTER.testing.v1, 'Testing']/items[at0018]")
        assertThat(firstNode).isNotNull
        assertThat(AmUtils.isNameConstrained(firstNode!!)).isTrue

        for ((i, code) in arrayOf("at0019", "at0020", "at0021").withIndex()) {
            val element = Element()
            element.archetypeNodeId = firstNode.nodeId
            element.name = create("local", code, "Name " + (i + 1))
            assertThat(AmUtils.matches(firstNode, element)).isTrue
        }

        val element = Element()
        element.archetypeNodeId = firstNode.nodeId
        element.name = DvText("Name")
        assertThat(AmUtils.matches(firstNode, element)).isFalse

        val secondNode = AmUtils.resolvePath(root, "/context/other_context[at0001]/items[openEHR-EHR-CLUSTER.testing.v1, 'Fixed name']")
        assertThat(AmUtils.isNameConstrained(secondNode!!)).isTrue

        val thirdNode = AmUtils.resolvePath(root, "/context/other_context[at0001]/items[openEHR-EHR-CLUSTER.testing.v1, 'Testing']")
        assertThat(AmUtils.isNameConstrained(thirdNode!!)).isFalse
    }

    @Test
    @Throws(IOException::class)
    fun testFindTermBindings() {
        val template = loadTemplate("/DogAPTrace-annot.opt")
        assertThat(template).isNotNull

        val builder = AmTreeBuilder(template)
        val root = builder.build()
        var extTermNode = AmUtils.resolvePath(
            root,
            "/content[openEHR-EHR-OBSERVATION.ap_clamp.v9]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value")

        assertThat(extTermNode).isNotNull
        var termBindings = AmUtils.findTermBindings(extTermNode!!, "at0004")
        assertThat(termBindings).hasSize(1)
        assertThat(termBindings.containsKey("MTH")).isTrue
        var item = termBindings["MTH"]
        assertThat(item!!.code).isEqualTo("at0004")
        assertThat(item.value.codeString).isEqualTo("123456")
        assertThat(item.value.terminologyId!!.value).isEqualTo("MTH(2016)")

        extTermNode = AmUtils.resolvePath(
            root,
            "/content[openEHR-EHR-OBSERVATION.ap_clamp.v9]/data[at0001]/events[at0002]/data[at0003]/items[at0005]")


        assertThat(extTermNode).isNotNull
        termBindings = AmUtils.findTermBindings(extTermNode!!, "at0005")
        assertThat(termBindings).hasSize(1)
        assertThat(termBindings.containsKey("MTH")).isTrue
        item = termBindings["MTH"]
        assertThat(item!!.code).isEqualTo("at0005")
        assertThat(item.value.codeString).isEqualTo("654321")
        assertThat(item.value.terminologyId!!.value).isEqualTo("MTH(2016)")

        extTermNode = AmUtils.resolvePath(
            root,
            "/content[openEHR-EHR-OBSERVATION.ap_clamp.v9]/data[at0001]/events[at0002]")

        assertThat(extTermNode).isNotNull
        termBindings = AmUtils.findTermBindings(extTermNode!!, "at0002")
        assertThat(termBindings).isEmpty()
    }
}
