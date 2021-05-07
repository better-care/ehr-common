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

import care.better.platform.template.exception.AmException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.openehr.am.aom.CComplexObject
import org.openehr.am.aom.CDvScale
import java.io.IOException

/**
 * @author Primoz Delopst
 * @author Bostjan Lah
 * @since 3.1.0
 */
class AmTreeBuilderTest : AbstractAmTest() {
    @Test
    @Throws(Exception::class)
    fun testVitalFunctions() {
        val template = loadTemplate("/ISPEK - ZN - Vital Functions Encounter.xml")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(root.name).isEqualTo("Vital functions")
    }

    @Test
    @Throws(Exception::class)
    fun testInitialMedicationSafetyReport() {
        val template = loadTemplate("/ISPEK - MSE - Initial Medication Safety Report.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(root.name).isEqualTo("Initial Medication Safety Report")
        assertThat(root.attributes["context"]!!.children[0].attributes.containsKey("end_time")).isTrue
    }

    @Test
    @Throws(Exception::class)
    fun testLinkType() {
        val template = loadTemplate("/ISPEK - MSE - Initial Medication Safety Report.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(root.attributes["links"]!!.children[0].rmType).isEqualTo("LINK")
    }

    @Test
    @Throws(Exception::class)
    fun testPerinatal() {
        val template = loadTemplate("/ISPEK - MED - Perinatal history Summary.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(root.name).isEqualTo("Perinatal history")
    }

    @Test
    @Throws(Exception::class)
    fun testCabinet() {
        val template = loadTemplate("/Cabinet.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        val amNode = AmUtils.resolvePath(root, "/content[openEHR-EHR-ADMIN_ENTRY.cabinet.v1]/data[at0001]/items[at0004]/value")
        assertThat(amNode!!.constraints).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun testAnnotations() {
        val template = loadTemplate("/Cabinet123.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()

        assertThat(
            AmUtils.resolvePath(
                root,
                "[openEHR-EHR-COMPOSITION.generic_misc.v1]/content[openEHR-EHR-ADMIN_ENTRY.cabinet.v1]/data[at0001]/items[at0004]")).isNotNull

        val amNode = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.generic_misc.v1]/content[openEHR-EHR-ADMIN_ENTRY.cabinet.v1]/data[at0001]/items[at0004]")

        assertThat(amNode!!.annotations!![0].items).extracting("id").containsOnly("GUI Directives.Widget Type", "GUI Directives.Show Description")
    }

    @Test
    @Throws(Exception::class)
    fun testAnnotationsTd27() {
        val template = loadTemplate("/Demo Vitals 27.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        val node = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-SECTION.ispek_dialog.v1]/items[openEHR-EHR-OBSERVATION.lab_test-hba1c.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]")

        assertThat(node).isNotNull
        assertThat(node!!.annotations!![0].items).extracting("id").containsOnly("GUI Directives.Hidden", "GUI Directives.Group")
    }

    @Test
    @Throws(Exception::class)
    fun testAnnotationsTd27fixed() {
        val template = loadTemplate("/Demo Vitals 27 1.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        val node = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-SECTION.ispek_dialog.v1]/items[openEHR-EHR-OBSERVATION.lab_test-hba1c.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]")

        assertThat(node).isNotNull
        assertThat(node!!.annotations!![0].items).extracting("id").containsOnly("GUI Directives.Hidden", "GUI Directives.Group")
        assertThat(node.annotations!![0].items).extracting("value").containsOnly("def", "abc")
    }

    @Test
    @Throws(Exception::class)
    fun testViewProperties() {
        val template = loadTemplate("/openEHR-EHR-COMPOSITION.t_allergologist_examination.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(
            AmUtils.resolvePath(
                root,
                "/content[openEHR-EHR-OBSERVATION.arterial_blood_pressure.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0014]/items[at0015]")).isNotNull

        val amNode = AmUtils.resolvePath(
            root,
            "/content[openEHR-EHR-OBSERVATION.arterial_blood_pressure.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0014]/items[at0015]")
        assertThat(amNode!!.viewConstraints).isNotEmpty
        assertThat(amNode.viewConstraints).extracting("id").containsOnly("pass_through")
    }

    @Test
    @Throws(Exception::class)
    fun testAllRmFields() {
        val template = loadTemplate("/XDS Document.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(root.attributes.containsKey("content")).isTrue
    }

    @Test
    @Throws(Exception::class)
    fun testNameRuntimeConstraints() {
        val template = loadTemplate("/TMC - ICU -Ventilator device Report.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(
            AmUtils.resolvePath(
                root,
                "/content[openEHR-EHR-SECTION.adhoc.v1,'NBP840']/items[openEHR-EHR-OBSERVATION.ventilator_vital_signs.v1,'NBP840  observtions']/data[at0001]/events[at0002]/data[at0003]/items[openEHR-EHR-CLUSTER.ventilator_settings2.v1,'Ventilator findings']/items[at0015,'PEEP']")).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun testPathResolver() {
        val template = loadTemplate("/Testing3.opt")
        val treeBuilder = AmTreeBuilder(template)
        val root = treeBuilder.build()
        assertThat(
            AmUtils.resolvePath(
                root,
                "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-EVALUATION.testing.v2]/data[at0001]/items[at0035]")).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun testAnnotationsOnClonedNodes() {
        val root = AmTreeBuilder(loadTemplate("/ISPEK - ZN - Assessment Scales Encounter.opt")).build()

        val firstNode = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-SECTION.ispek_dialog.v1,'Pain assessment']/items[openEHR-EHR-OBSERVATION.story.v1,'Story']/data[at0001]/events[at0002]/data[at0003]/items[openEHR-EHR-CLUSTER.symptom-pain-zn.v1]/items[at0018,'Relieving factor']")

        val secondNode = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-SECTION.ispek_dialog.v1,'Pain assessment']/items[openEHR-EHR-OBSERVATION.story.v1,'Story']/data[at0001]/events[at0002]/data[at0003]/items[openEHR-EHR-CLUSTER.symptom-pain-zn.v1]/items[at0018,'Exascerbating factor']")
        assertThat(firstNode!!.annotations).isNotEmpty
        assertThat(secondNode!!.annotations).isNotEmpty
    }

    @Test
    @Throws(Exception::class)
    fun testAnnotationsInTD27() {
        val root = AmTreeBuilder(loadTemplate("/parser_test2.7.opt")).build()
        val secondRoot = AmTreeBuilder(loadTemplate("/parser_test2.8.opt")).build()
        val rootFirstNode = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-OBSERVATION.glasgow_coma_scale.v1]/protocol[at0038]/items[at0039][openEHR-EHR-CLUSTER.exam_eye_pupil.v0]/items[at0003]"
        )
        assertThat(rootFirstNode).isNotNull

        val rootSecondNode = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-OBSERVATION.glasgow_coma_scale.v1]/protocol[at0038]/items[openEHR-EHR-CLUSTER.exam_eye_pupil.v0]/items[at0003]"
        )
        assertThat(rootSecondNode).isNotNull

        val secondRootFirstNode = AmUtils.resolvePath(
            secondRoot,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-OBSERVATION.glasgow_coma_scale.v1]/protocol[at0038]/items[at0039][openEHR-EHR-CLUSTER.exam_eye_pupil.v0]/items[at0003]")
        assertThat(secondRootFirstNode).isNotNull

        val secondRootSecondNode = AmUtils.resolvePath(
            secondRoot,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-OBSERVATION.glasgow_coma_scale.v1]/protocol[at0038]/items[openEHR-EHR-CLUSTER.exam_eye_pupil.v0]/items[at0003]")
        assertThat(secondRootSecondNode).isNotNull

        assertThat(rootFirstNode!!.viewConstraints).isNotEmpty
        assertThat(rootSecondNode!!.viewConstraints).isNotEmpty
        assertThat(secondRootFirstNode!!.viewConstraints).isNotEmpty
        assertThat(secondRootSecondNode!!.viewConstraints).isNotEmpty
    }

    @Test
    @Throws(IOException::class)
    fun testBrokenTemplate() {
        assertThat(AmTreeBuilder(loadTemplate("/Diabetes Check-up.opt")).build()).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun testInstructionExpiryTimeOccurences() {
        val root = AmTreeBuilder(loadTemplate("/Vaccination Encounter.opt")).build()
        val node = AmUtils.resolvePath(
            root,
            "[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-INSTRUCTION.vaccination_instruction.v0]/expiry_time")

        assertThat(node!!.occurrences.upper).isEqualTo(1)
    }

    @Test
    @Throws(Exception::class)
    fun testIntervalRangesNewTD() {
        assertThatThrownBy { AmTreeBuilder(loadTemplate("/test_interval.opt")).build() }
            .isInstanceOf(ClassCastException::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun testIntervalRangesOldTD() {
        val root = AmTreeBuilder(loadTemplate("/Medications 201711.opt")).build()
        assertThat(root).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun testTaskModel() {
        val root = AmTreeBuilder(loadTemplate("/Chemotherapy Work Plan (7).opt")).build()
        assertThat(root).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun testContextVariables() {
        val root = AmTreeBuilder(loadTemplate("/Acid Base Disorders Work Plan.opt")).build()
        assertThat(root).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun testTargetPath() {
        val root = AmTreeBuilder(loadTemplate("/ISPEK - MED - Medical Admission History.xml")).build()
        assertThat(root).isNotNull

        val fatherAge = "/content[openEHR-EHR-SECTION.ispek_dialog.v1, 'Family history']/items[openEHR-EHR-OBSERVATION.parental_growth_mnd.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/items[at0006]"
        val fatherHeight = "/content[openEHR-EHR-SECTION.ispek_dialog.v1, 'Family history']/items[openEHR-EHR-OBSERVATION.parental_growth_mnd.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/items[at0007]"
        assertThat(AmUtils.resolvePath(root, fatherAge)!!.name).isNotNull
        assertThat(AmUtils.resolvePath(root, fatherHeight)).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun testScale() {
        val root = AmTreeBuilder(loadTemplate("/scaleTest.opt")).build()
        assertThat(root).isNotNull

        val scales = root.attributes["content"]?.children?.get(0)
            ?.attributes?.get("data")?.children?.get(0)
            ?.attributes?.get("events")?.children?.get(0)
            ?.attributes?.get("data")?.children?.get(0)
            ?.attributes?.get("items")?.children ?: emptyList()

        assertThat(scales).hasSize(2)

        val firstScale = scales[0].attributes["value"]?.children?.get(0)
        assertThat(firstScale?.cObject).isInstanceOf(CDvScale::class.java)
        assertThat(firstScale?.rmType).isEqualTo("DV_SCALE")
        assertThat((firstScale?.cObject as CDvScale).list).hasSize(1)

        val secondScale = scales[1].attributes["value"]?.children?.get(0)
        assertThat(secondScale?.cObject).isInstanceOf(CComplexObject::class.java)
        assertThat(secondScale?.rmType).isEqualTo("DV_SCALE")
    }

    @Test
    @Throws(IOException::class)
    fun testNegativeDurations() {
        val root = AmTreeBuilder(loadTemplate("/negativeDurations.opt")).build()
        assertThat(root).isNotNull

        val durations = root.attributes["content"]?.children?.get(0)
            ?.attributes?.get("data")?.children?.get(0)
            ?.attributes?.get("events")?.children?.get(0)
            ?.attributes?.get("data")?.children?.get(0)
            ?.attributes?.get("items")?.children ?: emptyList()

        assertThat(durations).hasSize(3)
    }

    @Test
    fun testMissingReference() {
        assertThatThrownBy { AmTreeBuilder(loadTemplate("/ISPEK - ZN - Vital Functions Encounter (missing reference).opt")).build() }
            .isInstanceOf(AmException::class.java)
            .hasMessage("Referenced node on CLUSTER[openEHR-EHR-CLUSTER.distribution.v1] with path /items[at0003] not found.")
    }
}
