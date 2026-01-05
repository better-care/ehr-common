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
import org.openehr.base.basetypes.*
import org.openehr.rm.common.*
import org.openehr.rm.datastructures.History
import org.openehr.rm.datastructures.ItemTree
import org.openehr.rm.datatypes.*

/**
 * Tests visitor implementation for org.openehr.rm.composition package classes
 */
class RmCompositionVisitorTest : RmVisitorTest() {

    @Test
    fun `test Action visit`() {
        val instance = Action()
        // Locatable properties
        instance.name = DvText("Action name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-action-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ACTION.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Entry properties
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.provider = PartyIdentified().apply {
            name = "Dr. Test Provider"
            externalRef = PartyRef().apply {
                id = GenericId("provider-123", "test-scheme")
                namespace = "test-namespace"
                type = "PARTY"
            }
        }
        instance.workFlowId = ObjectRef().apply {
            id = HierObjectId("workflow-123")
            namespace = "local"
            type = "WORKFLOW"
        }
        instance.otherParticipations.add(Participation().apply {
            function = DvText("Assistant")
            performer = PartyIdentified().apply {
                name = "Test Assistant"
            }
            mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
        })
        // CareEntry properties
        instance.protocol = ItemTree().apply {
            archetypeNodeId = "at0003"
            name = DvText("Protocol")
        }
        instance.guidelineId = ObjectRef().apply {
            id = HierObjectId("guideline-456")
            namespace = "local"
            type = "GUIDELINE"
        }
        // Action properties
        instance.time = DvDateTime("2024-01-01T00:00:00Z")
        instance.description = ItemTree().apply {
            archetypeNodeId = "at0002"
            name = DvText("Description")
        }
        instance.ismTransition = IsmTransition().apply {
            currentState = DvCodedText(CodePhrase(TerminologyId("openehr"), "532"), "active")
            transition = DvCodedText(CodePhrase(TerminologyId("openehr"), "535"), "start")
            careflowStep = DvCodedText(CodePhrase(TerminologyId("local"), "at0004"), "Initial step")
        }
        instance.instructionDetails = InstructionDetails().apply {
            instructionId = LocatableRef().apply {
                id = HierObjectId("test-instruction-id")
                namespace = "local"
                type = "INSTRUCTION"
            }
            activityId = "test-activity-id"
            wfDetails = ItemTree().apply {
                archetypeNodeId = "at0005"
                name = DvText("Workflow Details")
            }
        }

        validateVisit(instance)
    }

    @Test
    fun `test Activity visit`() {
        val instance = Activity()
        // Locatable properties
        instance.name = DvText("Activity name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-activity-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ACTIVITY.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Activity properties
        instance.description = ItemTree().apply {
            archetypeNodeId = "at0002"
            name = DvText("Description")
        }
        instance.timing = DvParsable().apply {
            value = "P1D"
            formalism = "ISO8601"
        }
        instance.actionArchetypeId = "openEHR-EHR-ACTION.test.v1"

        validateVisit(instance)
    }

    @Test
    fun `test AdminEntry visit`() {
        val instance = AdminEntry()
        // Locatable properties
        instance.name = DvText("AdminEntry name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-adminentry-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ADMIN_ENTRY.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Entry properties
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.provider = PartyIdentified().apply {
            name = "Dr. Test Provider"
            externalRef = PartyRef().apply {
                id = GenericId("provider-123", "test-scheme")
                namespace = "test-namespace"
                type = "PARTY"
            }
        }
        instance.workFlowId = ObjectRef().apply {
            id = HierObjectId("workflow-123")
            namespace = "local"
            type = "WORKFLOW"
        }
        instance.otherParticipations.add(Participation().apply {
            function = DvText("Assistant")
            performer = PartyIdentified().apply {
                name = "Test Assistant"
            }
            mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
        })
        // AdminEntry properties
        instance.data = ItemTree().apply {
            archetypeNodeId = "at0002"
            name = DvText("Data")
        }

        validateVisit(instance)
    }

    @Test
    fun `test Composition visit`() {
        val instance = Composition()
        // Locatable properties
        instance.name = DvText("Composition name")
        instance.archetypeNodeId = "openEHR-EHR-COMPOSITION.test.v1"
        instance.uid = HierObjectId("test-composition-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-COMPOSITION.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Composition properties
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.territory = CodePhrase(TerminologyId("ISO_3166-1"), "US")
        instance.category = DvCodedText(CodePhrase(TerminologyId("openehr"), "433"), "event")
        instance.composer = PartySelf()
        instance.context = EventContext().apply {
            startTime = DvDateTime("2024-01-01T00:00:00Z")
            endTime = DvDateTime("2024-01-01T01:00:00Z")
            location = "Test Hospital"
            setting = DvCodedText(CodePhrase(TerminologyId("openehr"), "225"), "home")
            otherContext = ItemTree().apply {
                archetypeNodeId = "at0003"
                name = DvText("Other Context")
            }
            healthCareFacility = PartyIdentified().apply {
                name = "Test Hospital"
                externalRef = PartyRef().apply {
                    id = GenericId("hospital-123", "test-scheme")
                    namespace = "test-namespace"
                    type = "PARTY"
                }
            }
            participations.add(Participation().apply {
                function = DvText("Nurse")
                performer = PartyIdentified().apply {
                    name = "Test Nurse"
                }
                mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
            })
        }
        instance.content.add(Section().apply {
            name = DvText("Test Section")
            archetypeNodeId = "at0004"
        })

        validateVisit(instance)
    }

    @Test
    fun `test Evaluation visit`() {
        val instance = Evaluation()
        // Locatable properties
        instance.name = DvText("Evaluation name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-evaluation-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-EVALUATION.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Entry properties
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.provider = PartyIdentified().apply {
            name = "Dr. Test Provider"
            externalRef = PartyRef().apply {
                id = GenericId("provider-123", "test-scheme")
                namespace = "test-namespace"
                type = "PARTY"
            }
        }
        instance.workFlowId = ObjectRef().apply {
            id = HierObjectId("workflow-123")
            namespace = "local"
            type = "WORKFLOW"
        }
        instance.otherParticipations.add(Participation().apply {
            function = DvText("Assistant")
            performer = PartyIdentified().apply {
                name = "Test Assistant"
            }
            mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
        })
        // CareEntry properties
        instance.protocol = ItemTree().apply {
            archetypeNodeId = "at0003"
            name = DvText("Protocol")
        }
        instance.guidelineId = ObjectRef().apply {
            id = HierObjectId("guideline-456")
            namespace = "local"
            type = "GUIDELINE"
        }
        // Evaluation properties
        instance.data = ItemTree().apply {
            archetypeNodeId = "at0002"
            name = DvText("Data")
        }

        validateVisit(instance)
    }

    @Test
    fun `test EventContext visit`() {
        val instance = EventContext()
        // EventContext properties (not a Locatable)
        instance.startTime = DvDateTime("2024-01-01T00:00:00Z")
        instance.endTime = DvDateTime("2024-01-01T01:00:00Z")
        instance.location = "Test Hospital Ward"
        instance.setting = DvCodedText(CodePhrase(TerminologyId("openehr"), "225"), "home")
        instance.otherContext = ItemTree().apply {
            archetypeNodeId = "at0001"
            name = DvText("Other Context")
        }
        instance.healthCareFacility = PartyIdentified().apply {
            name = "Test Hospital"
            externalRef = PartyRef().apply {
                id = GenericId("hospital-123", "test-scheme")
                namespace = "test-namespace"
                type = "PARTY"
            }
        }
        instance.participations.add(Participation().apply {
            function = DvText("Nurse")
            performer = PartyIdentified().apply {
                name = "Test Nurse"
            }
            mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
        })

        validateVisit(instance)
    }

    @Test
    fun `test Instruction visit`() {
        val instance = Instruction()
        // Locatable properties
        instance.name = DvText("Instruction name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-instruction-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-INSTRUCTION.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Entry properties
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.provider = PartyIdentified().apply {
            name = "Dr. Test Provider"
            externalRef = PartyRef().apply {
                id = GenericId("provider-123", "test-scheme")
                namespace = "test-namespace"
                type = "PARTY"
            }
        }
        instance.workFlowId = ObjectRef().apply {
            id = HierObjectId("workflow-123")
            namespace = "local"
            type = "WORKFLOW"
        }
        instance.otherParticipations.add(Participation().apply {
            function = DvText("Assistant")
            performer = PartyIdentified().apply {
                name = "Test Assistant"
            }
            mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
        })
        // CareEntry properties
        instance.protocol = ItemTree().apply {
            archetypeNodeId = "at0003"
            name = DvText("Protocol")
        }
        instance.guidelineId = ObjectRef().apply {
            id = HierObjectId("guideline-456")
            namespace = "local"
            type = "GUIDELINE"
        }
        // Instruction properties
        instance.narrative = DvText("Test narrative")
        instance.expiryTime = DvDateTime("2024-12-31T23:59:59Z")
        instance.wfDefinition = DvParsable().apply {
            value = "workflow definition"
            formalism = "text/plain"
        }
        instance.activities.add(Activity().apply {
            name = DvText("Test Activity")
            archetypeNodeId = "at0004"
            description = ItemTree().apply {
                archetypeNodeId = "at0005"
                name = DvText("Activity Description")
            }
            actionArchetypeId = "openEHR-EHR-ACTION.test.v1"
        })

        validateVisit(instance)
    }

    @Test
    fun `test InstructionDetails visit`() {
        val instance = InstructionDetails()
        // InstructionDetails properties (not a Locatable)
        instance.instructionId = LocatableRef().apply {
            id = HierObjectId("test-instruction-id")
            namespace = "local"
            type = "INSTRUCTION"
        }
        instance.activityId = "test-activity-id"
        instance.wfDetails = ItemTree().apply {
            archetypeNodeId = "at0001"
            name = DvText("Workflow Details")
        }

        validateVisit(instance)
    }

    @Test
    fun `test IsmTransition visit`() {
        val instance = IsmTransition()
        // IsmTransition properties (not a Locatable)
        instance.currentState = DvCodedText(CodePhrase(TerminologyId("openehr"), "532"), "active")
        instance.transition = DvCodedText(CodePhrase(TerminologyId("openehr"), "535"), "start")
        instance.careflowStep = DvCodedText(CodePhrase(TerminologyId("local"), "at0001"), "Initial step")
        instance.reason.add(DvText("Test reason 1"))
        instance.reason.add(DvText("Test reason 2"))

        validateVisit(instance)
    }

    @Test
    fun `test Observation visit`() {
        val instance = Observation()
        // Locatable properties
        instance.name = DvText("Observation name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-observation-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-OBSERVATION.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Entry properties
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
        instance.subject = PartySelf()
        instance.provider = PartyIdentified().apply {
            name = "Dr. Test Provider"
            externalRef = PartyRef().apply {
                id = GenericId("provider-123", "test-scheme")
                namespace = "test-namespace"
                type = "PARTY"
            }
        }
        instance.workFlowId = ObjectRef().apply {
            id = HierObjectId("workflow-123")
            namespace = "local"
            type = "WORKFLOW"
        }
        instance.otherParticipations.add(Participation().apply {
            function = DvText("Assistant")
            performer = PartyIdentified().apply {
                name = "Test Assistant"
            }
            mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face")
        })
        // CareEntry properties
        instance.protocol = ItemTree().apply {
            archetypeNodeId = "at0003"
            name = DvText("Protocol")
        }
        instance.guidelineId = ObjectRef().apply {
            id = HierObjectId("guideline-456")
            namespace = "local"
            type = "GUIDELINE"
        }
        // Observation properties
        instance.data = History().apply {
            archetypeNodeId = "at0002"
            name = DvText("Data")
            origin = DvDateTime("2024-01-01T00:00:00Z")
        }
        instance.state = History().apply {
            archetypeNodeId = "at0004"
            name = DvText("State")
            origin = DvDateTime("2024-01-01T00:00:00Z")
        }

        validateVisit(instance)
    }

    @Test
    fun `test Section visit`() {
        val instance = Section()
        // Locatable properties
        instance.name = DvText("Section name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-section-uid::1.0.0")
        instance.links.add(Link().apply {
            meaning = DvText("test link")
            type = DvText("related")
            target = DvEhrUri("ehr://test-link-target")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-SECTION.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit().apply {
            originatingSystemAudit = FeederAuditDetails().apply {
                systemId = "test-system"
            }
        }
        // Section properties
        instance.items.add(Evaluation().apply {
            name = DvText("Test Evaluation")
            archetypeNodeId = "at0002"
            language = CodePhrase(TerminologyId("ISO_639-1"), "en")
            encoding = CodePhrase(TerminologyId("IANA_character-sets"), "UTF-8")
            subject = PartySelf()
            data = ItemTree().apply {
                archetypeNodeId = "at0003"
                name = DvText("Evaluation Data")
            }
        })

        validateVisit(instance)
    }
}
