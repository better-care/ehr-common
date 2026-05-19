package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.visitor.RmVisitorContext
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType
import kotlinx.serialization.SerialName
import org.openehr.base.basetypes.ObjectRef
import java.io.Serializable

/**
 * @author Dušan Marković
 * @since 5.0.0
 */

@Open
@SerialName("ITEM_TAG")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITEM_TAG", propOrder = ["key", "value", "target", "target_path", "owner_id"])
@kotlinx.serialization.Serializable
class ItemTag : RmObject(), Serializable {

    @Required
    @XmlElement(required = true)
    var key: String? = null

    var value: String? = null

    var target: ObjectRef? = null

    @XmlElement(name = "target_path")
    @SerialName("target_path")
    var targetPath: String? = null

    @XmlElement(name = "owner_id")
    @SerialName("owner_id")
    var ownerId: ObjectRef? = null

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "ITEM_TAG") {
            visitProperties(ctx)
        }
    }

    internal fun visitProperties(ctx: RmVisitorContext) {
        key?.also { ctx.visitValue("key", it, this) }
        value?.also { ctx.visitValue("value", it, this) }
        target?.visit("target", ctx)
        targetPath?.also { ctx.visitValue("target_path", it, this) }
        ownerId?.visit("owner_id", ctx)
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }
}
