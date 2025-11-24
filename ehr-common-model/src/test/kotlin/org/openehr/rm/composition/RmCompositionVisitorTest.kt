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

package org.openehr.rm.composition

import care.better.platform.visitor.RmVisitorTest
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.LocatableRef
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datastructures.ItemTree
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvText

/**
 * Tests visitor implementation for org.openehr.rm.composition package classes
 */
class RmCompositionVisitorTest : RmVisitorTest() {

    @Test
    fun `test Action visit`() {
        val instance = Action()
        instance.name = DvText("Action name")
        instance.archetypeNodeId = "at0001"
        instance.time = DvDateTime("2024-01-01T00:00:00Z")
        instance.description = ItemTree()
        instance.description?.archetypeNodeId = "at0002"
        instance.description?.name = DvText("Description")
        instance.ismTransition = IsmTransition()
        instance.ismTransition?.currentState = DvCodedText(CodePhrase(TerminologyId("openehr"), "532"), "active")

        validateVisit(instance)
    }

    @Test
    fun `test Activity visit`() {
        val instance = Activity()
        instance.name = DvText("Activity name")
        instance.archetypeNodeId = "at0001"
        instance.description = ItemTree()
        instance.description?.archetypeNodeId = "at0002"
        instance.description?.name = DvText("Description")
        instance.actionArchetypeId = "openEHR-EHR-ACTION.test.v1"

        validateVisit(instance)
    }

    @Test
    fun `test AdminEntry visit`() {
        val instance = AdminEntry()
        instance.name = DvText("AdminEntry name")
        instance.archetypeNodeId = "at0001"
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.data = ItemTree()
        instance.data?.archetypeNodeId = "at0002"
        instance.data?.name = DvText("Data")

        validateVisit(instance)
    }

    @Test
    fun `test Composition visit`() {
        val instance = Composition()
        instance.name = DvText("Composition name")
        instance.archetypeNodeId = "openEHR-EHR-COMPOSITION.test.v1"
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.territory = CodePhrase(TerminologyId("ISO_3166-1"), "US")
        instance.category = DvCodedText(CodePhrase(TerminologyId("openehr"), "433"), "event")
        instance.composer = PartySelf()

        validateVisit(instance)
    }

    @Test
    fun `test Evaluation visit`() {
        val instance = Evaluation()
        instance.name = DvText("Evaluation name")
        instance.archetypeNodeId = "at0001"
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.data = ItemTree()
        instance.data?.archetypeNodeId = "at0002"
        instance.data?.name = DvText("Data")

        validateVisit(instance)
    }

    @Test
    fun `test EventContext visit`() {
        val instance = EventContext()
        instance.startTime = DvDateTime("2024-01-01T00:00:00Z")
        instance.setting = DvCodedText(CodePhrase(TerminologyId("openehr"), "225"), "home")

        validateVisit(instance)
    }

    @Test
    fun `test Instruction visit`() {
        val instance = Instruction()
        instance.name = DvText("Instruction name")
        instance.archetypeNodeId = "at0001"
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.narrative = DvText("Test narrative")

        validateVisit(instance)
    }

    @Test
    fun `test InstructionDetails visit`() {
        val instance = InstructionDetails()
        instance.instructionId = LocatableRef()
        instance.instructionId!!.id = HierObjectId("test-instruction-id")
        instance.activityId = "test-activity-id"

        validateVisit(instance)
    }

    @Test
    fun `test IsmTransition visit`() {
        val instance = IsmTransition()
        instance.currentState = DvCodedText(CodePhrase(TerminologyId("openehr"), "532"), "active")

        validateVisit(instance)
    }

    @Test
    fun `test Observation visit`() {
        val instance = Observation()
        instance.name = DvText("Observation name")
        instance.archetypeNodeId = "at0001"
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.data = org.openehr.rm.datastructures.History()
        instance.data?.archetypeNodeId = "at0002"
        instance.data?.name = DvText("Data")
        instance.data?.origin = DvDateTime("2024-01-01T00:00:00Z")

        validateVisit(instance)
    }

    @Test
    fun `test Section visit`() {
        val instance = Section()
        instance.name = DvText("Section name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }
}
