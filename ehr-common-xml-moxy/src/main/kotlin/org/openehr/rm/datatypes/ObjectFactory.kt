package org.openehr.rm.datatypes

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createCodePhrase(): CodePhrase = CodePhrase()
    fun createDvAmount(): DvAmount = DvAmount()
    fun createDvBoolean(): DvBoolean = DvBoolean()
    fun createDvCodedText(): DvCodedText = DvCodedText()
    fun createDvCount(): DvCount = DvCount()
    fun createDvDate(): DvDate = DvDate()
    fun createDvDateTime(): DvDateTime = DvDateTime()
    fun createDvDuration(): DvDuration = DvDuration()
    fun createDvEhrUri(): DvEhrUri = DvEhrUri()
    fun createDvGeneralTimeSpecification(): DvGeneralTimeSpecification = DvGeneralTimeSpecification()
    fun createDvIdentifier(): DvIdentifier = DvIdentifier()
    fun createDvInterval(): DvInterval = DvInterval()
    fun createDvMultimedia(): DvMultimedia = DvMultimedia()
    fun createDvOrdinal(): DvOrdinal = DvOrdinal()
    fun createDvParagraph(): DvParagraph = DvParagraph()
    fun createDvParsable(): DvParsable = DvParsable()
    fun createDvPeriodicTimeSpecification(): DvPeriodicTimeSpecification = DvPeriodicTimeSpecification()
    fun createDvProportion(): DvProportion = DvProportion()
    fun createDvQuantity(): DvQuantity = DvQuantity()
    fun createDvState(): DvState = DvState()
    fun createDvTemporal(): DvTemporal = DvTemporal()
    fun createDvText(): DvText = DvText()
    fun createDvTime(): DvTime = DvTime()
    fun createDvUri(): DvUri = DvUri()
    fun createReferenceRange(): ReferenceRange = ReferenceRange()
    fun createTermMapping(): TermMapping = TermMapping()
}