package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import java.io.Serializable
import java.util.*

/**
 * @author Primoz Delopst
 */

abstract class ExprTypeDef<T>() : RmObject(), Serializable {

    private var typeName: String? = null

    constructor(typeName: String?) : this() {
        this.typeName = typeName
    }

    open fun setTypeName(typeName: String?) {
        this.typeName = typeName
    }

    override fun hashCode(): Int = Objects.hash(typeName)

    override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                other == null || javaClass != other.javaClass -> false
                else -> (other as ExprTypeDef<*>).typeName == typeName
            }

    override fun toString(): String =
            "ExprTypeDef{" +
                    "typeName='$typeName'" +
                    '}'
}