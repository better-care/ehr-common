package care.better.platform.visitor

import care.better.openehr.rm.RmObject
import org.openehr.rm.common.Locatable

interface RmVisitorContext {
    fun visitLocatable(attributeName: String, locatable: Locatable, typeName: String): Boolean = true
    fun beforeLocatable(attributeName: String, locatable: Locatable, typeName: String): Boolean = true
    fun afterLocatable(attributeName: String, locatable: Locatable, typeName: String)
    fun visitObject(attributeName: String, value: RmObject, typeName: String): Boolean = true
    fun beforeObject(attributeName: String, value: RmObject, typeName: String): Boolean = true
    fun afterObject(attributeName: String, value: RmObject, typeName: String)
    fun visitValue(attributeName: String, value: Any): Boolean = true
}
