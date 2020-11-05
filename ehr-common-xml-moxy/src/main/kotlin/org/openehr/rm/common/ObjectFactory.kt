package org.openehr.rm.common

import org.openehr.base.basetypes.ArchetypeId
import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlElementDecl
import javax.xml.bind.annotation.XmlRegistry
import javax.xml.namespace.QName

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {

    companion object {
        private val ITEMS_QNAME = QName("http://schemas.openehr.org/v1", "items")
        private val CONTRIBUTION_QNAME = QName("http://schemas.openehr.org/v1", "contribution")
        private val VERSION_QNAME = QName("http://schemas.openehr.org/v1", "version")
    }

    fun createArchetypeId(): ArchetypeId = ArchetypeId()
    fun createAttestation(): Attestation = Attestation()
    fun createAuditDetails(): AuditDetails = AuditDetails()
    fun createContribution(): Contribution = Contribution()
    fun createFeederAudit(): FeederAudit = FeederAudit()
    fun createFeederAuditDetails(): FeederAuditDetails = FeederAuditDetails()
    fun createFolder(): Folder = Folder()
    fun createImportedVersion(): ImportedVersion = ImportedVersion()
    fun createLink(): Link = Link()
    fun createOriginalVersion(): OriginalVersion = OriginalVersion()
    fun createParticipatio(): Participation = Participation()
    fun createPartyIdentified(): PartyIdentified = PartyIdentified()
    fun createPartyRelated(): PartyRelated = PartyRelated()
    fun createPartySelf(): PartySelf = PartySelf()
    fun createResourceDescription(): ResourceDescription = ResourceDescription()
    fun createResourceDescriptionItem(): ResourceDescriptionItem = ResourceDescriptionItem()
    fun createRevisionHistory(): RevisionHistory = RevisionHistory()
    fun createRevisionHistoryItem(): RevisionHistoryItem = RevisionHistoryItem()
    fun createStringDictionaryItem(): StringDictionaryItem = StringDictionaryItem()
    fun createVersionedObject(): VersionedObject = VersionedObject()

    @XmlElementDecl(namespace = "http://schemas.openehr.org/v1", name = "items")
    fun createItems(value: Locatable): JAXBElement<Locatable> = JAXBElement(ITEMS_QNAME, Locatable::class.java, null, value)


    @XmlElementDecl(namespace = "http://schemas.openehr.org/v1", name = "contribution")
    fun createContribution(value: Contribution): JAXBElement<Contribution> =
            JAXBElement(CONTRIBUTION_QNAME, Contribution::class.java, null, value)


    @XmlElementDecl(namespace = "http://schemas.openehr.org/v1", name = "version")
    fun createVersion(value: Version): JAXBElement<Version> =
            JAXBElement(VERSION_QNAME, Version::class.java, null, value)
}