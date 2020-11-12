package org.openehr.proc.taskplanning

import java.net.URI

/**
 * @author Primoz Delopst
 */

class TypeDefUri : ExprTypeDef<URI>("Uri") {

    companion object {
        val INSTANCE: TypeDefUri = TypeDefUri()
    }

    override fun setTypeName(typeName: String?) {
        if ("Uri" != typeName) {
            throw UnsupportedOperationException("The type name has to be Uri")
        }
        super.setTypeName(typeName)
    }


    override fun toString(): String = "TypeDefUri{} ${super.toString()}"
}