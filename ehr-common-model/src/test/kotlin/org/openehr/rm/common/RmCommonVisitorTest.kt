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
import org.openehr.base.basetypes.*
import org.openehr.rm.datastructures.ItemTree
import org.openehr.rm.datatypes.*

/**
 * Tests visitor implementation for org.openehr.rm.common package classes
 */
class RmCommonVisitorTest : RmVisitorTest() {

    @Test
    fun `test Archetyped visit`() {
        val instance = Archetyped()
        instance.archetypeId = ArchetypeId("openEHR-EHR-OBSERVATION.test.v1")
        instance.templateId = TemplateId("test-template")
        instance.rmVersion = "1.0.4"

        validateVisit(instance)
    }

    @Test
    fun `test Attestation visit`() {
        val instance = Attestation()
        // AuditDetails properties
        instance.systemId = "test-system"
        instance.committer = PartySelf()
        instance.timeCommitted = DvDateTime("2024-01-01T00:00:00Z")
        instance.changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "249"), "creation")
        instance.description = DvText("Test audit description")

        // Attestation properties
        instance.attestedView = DvMultimedia()
        instance.attestedView!!.mediaType = CodePhrase(TerminologyId("IANA_media-types"), "text/plain")
        instance.proof = "test-proof-string"
        instance.items = mutableListOf(DvEhrUri("ehr://test/item/1"), DvEhrUri("ehr://test/item/2"))
        instance.reason = DvText("Test reason")
        instance.isPending = false

        validateVisit(instance)
    }

    @Test
    fun `test AuditDetails visit`() {
        val instance = AuditDetails()
        instance.systemId = "test-system"
        instance.committer = PartySelf()
        instance.timeCommitted = DvDateTime("2024-01-01T00:00:00Z")
        instance.changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "249"), "creation")
        instance.description = DvText("Test description")

        validateVisit(instance)
    }

    @Test
    fun `test FeederAudit visit`() {
        val instance = FeederAudit()
        instance.originatingSystemItemIds = mutableListOf(
            DvIdentifier("test-id-1", "test-issuer", "test-assigner", "id-type-1"),
            DvIdentifier("test-id-2", "test-issuer", "test-assigner", "id-type-2")
        )
        instance.feederSystemItemIds = mutableListOf(
            DvIdentifier("feeder-id-1", "feeder-issuer", "feeder-assigner", "feeder-type")
        )
        instance.originalContent = DvParsable("test content", "text/plain")
        instance.originatingSystemAudit = FeederAuditDetails()
        instance.originatingSystemAudit!!.systemId = "originating-system"
        instance.feederSystemAudit = FeederAuditDetails()
        instance.feederSystemAudit!!.systemId = "feeder-system"

        validateVisit(instance)
    }

    @Test
    fun `test FeederAuditDetails visit`() {
        val instance = FeederAuditDetails()
        instance.systemId = "test-system"
        instance.location = PartyIdentified("Test Location")
        instance.provider = PartyIdentified("Test Provider")
        instance.subject = PartySelf()
        instance.time = DvDateTime("2024-01-01T10:30:00Z")
        instance.versionId = "1.0.0"
        instance.otherDetails = ItemTree()

        validateVisit(instance)
    }

    @Test
    fun `test Folder visit`() {
        val instance = Folder()
        // Locatable properties
        instance.name = DvText("Test folder")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-folder-uid")
        instance.links = mutableListOf(
            Link(DvText("link meaning"), DvText("link type"), DvEhrUri("ehr://test/link"))
        )
        instance.archetypeDetails = Archetyped()
        instance.archetypeDetails!!.archetypeId = ArchetypeId("openEHR-EHR-FOLDER.test.v1")
        instance.archetypeDetails!!.rmVersion = "1.0.4"
        instance.feederAudit = FeederAudit()
        instance.feederAudit!!.originatingSystemAudit = FeederAuditDetails()
        instance.feederAudit!!.originatingSystemAudit!!.systemId = "feeder-system"

        // Folder properties
        instance.folders = mutableListOf(
            Folder().apply {
                name = DvText("Sub folder")
                archetypeNodeId = "at0002"
            }
        )
        instance.items = mutableListOf(
            ObjectRef(ObjectVersionId("test-obj-1::test-system::1"), "local", "COMPOSITION"),
            ObjectRef(ObjectVersionId("test-obj-2::test-system::1"), "local", "OBSERVATION")
        )
        instance.details = ItemTree()

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
        instance.mode = DvCodedText(CodePhrase(TerminologyId("openehr"), "193"), "face-to-face communication")
        instance.time = DvInterval(DvDateTime("2024-01-01T09:00:00Z"), DvDateTime("2024-01-01T17:00:00Z"))

        validateVisit(instance)
    }

    @Test
    fun `test PartyIdentified visit`() {
        val instance = PartyIdentified()
        // PartyProxy property
        instance.externalRef = PartyRef(ObjectVersionId("party-123::test-system::1"), "local", "PARTY")

        // PartyIdentified properties
        instance.name = "Test Party"
        instance.identifiers = mutableListOf(
            DvIdentifier("id-123", "issuer-org", "assigner-org", "id-type-1"),
            DvIdentifier("id-456", "issuer-org", "assigner-org", "id-type-2")
        )

        validateVisit(instance)
    }

    @Test
    fun `test PartyRelated visit`() {
        val instance = PartyRelated()
        // PartyProxy property
        instance.externalRef = PartyRef(ObjectVersionId("related-party-789::test-system::1"), "local", "PARTY")

        // PartyIdentified properties
        instance.name = "Test Related Party"
        instance.identifiers = mutableListOf(
            DvIdentifier("related-id-1", "issuer", "assigner", "type-1")
        )

        // PartyRelated property
        instance.relationship = DvCodedText(CodePhrase(TerminologyId("openehr"), "0"), "self")

        validateVisit(instance)
    }

    @Test
    fun `test PartySelf visit`() {
        val instance = PartySelf()
        // PartyProxy property
        instance.externalRef = PartyRef(ObjectVersionId("self-party-456::test-system::1"), "local", "PARTY")

        validateVisit(instance)
    }

    @Test
    fun `test ResourceDescriptionItem visit`() {
        val instance = ResourceDescriptionItem()
        instance.language = CodePhrase(TerminologyId("ISO_639-1"), "en")
        instance.purpose = "Test purpose"
        instance.keywords = mutableListOf("keyword1", "keyword2", "keyword3")
        instance.use = "Test use description"
        instance.misuse = "Test misuse description"
        instance.copyright = "Copyright 2024 Test Organization"
        instance.originalResourceUri = mutableListOf(
            StringDictionaryItem().apply {
                id = "uri1"
                value = "http://example.com/resource1"
            },
            StringDictionaryItem().apply {
                id = "uri2"
                value = "http://example.com/resource2"
            }
        )
        instance.otherDetails = mutableListOf(
            StringDictionaryItem().apply {
                id = "detail1"
                value = "detail value 1"
            }
        )

        validateVisit(instance)
    }

    @Test
    fun `test RevisionHistory visit`() {
        val instance = RevisionHistory()
        instance.items = mutableListOf(
            RevisionHistoryItem().apply {
                versionId = ObjectVersionId("test-object::test-system::1")
                audits = mutableListOf(
                    AuditDetails().apply {
                        systemId = "test-system"
                        committer = PartySelf()
                        timeCommitted = DvDateTime("2024-01-01T00:00:00Z")
                        changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "249"), "creation")
                    }
                )
            },
            RevisionHistoryItem().apply {
                versionId = ObjectVersionId("test-object::test-system::2")
                audits = mutableListOf(
                    AuditDetails().apply {
                        systemId = "test-system"
                        committer = PartySelf()
                        timeCommitted = DvDateTime("2024-01-02T00:00:00Z")
                        changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "251"), "modification")
                    }
                )
            }
        )

        validateVisit(instance)
    }

    @Test
    fun `test RevisionHistoryItem visit`() {
        val instance = RevisionHistoryItem()
        instance.versionId = ObjectVersionId("test-object::test-system::1")
        instance.audits = mutableListOf(
            AuditDetails().apply {
                systemId = "test-system-1"
                committer = PartySelf()
                timeCommitted = DvDateTime("2024-01-01T00:00:00Z")
                changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "249"), "creation")
                description = DvText("Initial creation")
            },
            AuditDetails().apply {
                systemId = "test-system-2"
                committer = PartyIdentified("Test Committer")
                timeCommitted = DvDateTime("2024-01-01T01:00:00Z")
                changeType = DvCodedText(CodePhrase(TerminologyId("openehr"), "251"), "modification")
                description = DvText("First modification")
            }
        )

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
