package org.openehr.base.resource

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createTranslationDetails(): TranslationDetails = TranslationDetails()
}