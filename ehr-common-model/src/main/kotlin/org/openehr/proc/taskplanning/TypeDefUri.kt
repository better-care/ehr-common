package org.openehr.proc.taskplanning

import java.net.URI

/**
 * @author Primoz Delopst
 */

class TypeDefUri : ExprTypeDef<URI>("Uri") {

    companion object {
        val INSTANCE: TypeDefUri = TypeDefUri()
    }

    override fun toString(): String = "TypeDefUri{} ${super.toString()}"
}