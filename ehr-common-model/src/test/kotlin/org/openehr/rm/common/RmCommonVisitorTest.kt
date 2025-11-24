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

package org.openehr.rm.common

import care.better.platform.visitor.RmVisitorTest
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.datatypes.*

/**
 * Tests visitor implementation for org.openehr.rm.common package classes
 */
class RmCommonVisitorTest : RmVisitorTest() {

    @Test
    fun `test Archetyped visit`() {
        val instance = Archetyped()
        instance.archetypeId = ArchetypeId("openEHR-EHR-OBSERVATION.test.v1")
        instance.rmVersion = "1.0.4"

        validateVisit(instance)
    }

    @Test
    fun `test Attestation visit`() {
        val instance = Attestation()
        instance.reason = DvText("Test reason")
        instance.systemId = "test-system"
        instance.committer = PartySelf()
        instance.timeCommitted = DvDateTime("2024-01-01T00:00:00Z")
        instance.changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "249"), "creation")

        validateVisit(instance)
    }

    @Test
    fun `test AuditDetails visit`() {
        val instance = AuditDetails()
        instance.systemId = "test-system"
        instance.committer = PartySelf()
        instance.timeCommitted = DvDateTime("2024-01-01T00:00:00Z")
        instance.changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "249"), "creation")

        validateVisit(instance)
    }

    @Test
    fun `test FeederAudit visit`() {
        val instance = FeederAudit()

        validateVisit(instance)
    }

    @Test
    fun `test FeederAuditDetails visit`() {
        val instance = FeederAuditDetails()
        instance.systemId = "test-system"

        validateVisit(instance)
    }

    @Test
    fun `test Folder visit`() {
        val instance = Folder()
        instance.name = DvText("Test folder")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test Link visit`() {
        val instance = Link()
        instance.meaning = DvText("link meaning")
        instance.type = DvText("link type")
        instance.target = DvEhrUri("ehr://test")

        validateVisit(instance)
    }

    @Test
    fun `test Participation visit`() {
        val instance = Participation()
        instance.function = DvText("test function")
        instance.performer = PartySelf()

        validateVisit(instance)
    }

    @Test
    fun `test PartyIdentified visit`() {
        val instance = PartyIdentified()
        instance.name = "Test Party"

        validateVisit(instance)
    }

    @Test
    fun `test PartyRelated visit`() {
        val instance = PartyRelated()
        instance.name = "Test Related Party"
        instance.relationship = DvCodedText(CodePhrase(TerminologyId("openehr"), "0"), "self")

        validateVisit(instance)
    }

    @Test
    fun `test PartySelf visit`() {
        val instance = PartySelf()

        validateVisit(instance)
    }

    @Test
    fun `test ResourceDescriptionItem visit`() {
        val instance = ResourceDescriptionItem()
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.purpose = "Test purpose"

        validateVisit(instance)
    }

    @Test
    fun `test RevisionHistory visit`() {
        val instance = RevisionHistory()

        validateVisit(instance)
    }

    @Test
    fun `test RevisionHistoryItem visit`() {
        val instance = RevisionHistoryItem()

        validateVisit(instance)
    }

    @Test
    fun `test StringDictionaryItem visit`() {
        val instance = StringDictionaryItem()
        instance.id = "test-id"
        instance.value = "test-value"

        validateVisit(instance)
    }
}
