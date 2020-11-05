package org.openehr.rm.integration

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createGenericEntry(): GenericEntry = GenericEntry()
}