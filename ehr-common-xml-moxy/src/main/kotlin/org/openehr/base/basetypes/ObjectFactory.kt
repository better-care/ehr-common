package org.openehr.base.basetypes

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createAccessGroupRef(): AccessGroupRef = AccessGroupRef()
    fun createArchetypeId(): ArchetypeId = ArchetypeId()
    fun createGenericId(): GenericId = GenericId()
    fun createHierObjectId(): HierObjectId = HierObjectId()
    fun createLocatableRef(): LocatableRef = LocatableRef()
    fun createObjectRef(): ObjectRef = ObjectRef()
    fun createObjectVersionId(): ObjectVersionId = ObjectVersionId()
    fun createPartyRef(): PartyRef = PartyRef()
    fun createTemplateId(): TemplateId = TemplateId()
    fun createTerminologyId(): TerminologyId = TerminologyId()
}