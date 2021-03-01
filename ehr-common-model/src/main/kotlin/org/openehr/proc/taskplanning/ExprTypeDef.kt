/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import java.io.Serializable
import java.util.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "EXPR_TYPE_DEF", propOrder = ["typeName"])
@XmlSeeAlso(
    value = [
        TypeDefBoolean::class,
        TypeDefReal::class,
        TypeDefInteger::class,
        TypeDefDate::class,
        TypeDefDateTime::class,
        TypeDefDuration::class,
        TypeDefObject::class,
        TypeDefObjectRef::class,
        TypeDefString::class,
        TypeDefVoid::class,
        TypeDefTerminologyCode::class,
        TypeDefUri::class])
@Open
abstract class ExprTypeDef<T>(@XmlElement(name = "type_name", required = true) val typeName: String) : RmObject(), Serializable {

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    fun setTypeName(typeName: String) {
        if (typeName != this.typeName) {
            throw UnsupportedOperationException("The type name has to be ${this.typeName}")
        }
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
