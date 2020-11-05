package org.openehr.rm.ehr

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createEhr(): Ehr = Ehr()
    fun createEhrStatus(): EhrStatus = EhrStatus()
}