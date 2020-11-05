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

import org.openehr.base.basetypes.ObjectRef
import java.time.*
import java.util.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
enum class ExpressionType(val expressionTypeDef: ExprTypeDef<*>?, val javaClass: Class<*>) {
    BOOLEAN(TypeDefBoolean.INSTANCE, Boolean::class.java),
    DECIMAL(TypeDefReal.INSTANCE, Number::class.java),
    INTEGER(TypeDefInteger.INSTANCE, Integer::class.java),
    LONG(null, Long::class.java),
    STRING(TypeDefString.INSTANCE, String::class.java),
    DATE(TypeDefDate.INSTANCE, LocalDate::class.java),
    TIME(null, LocalTime::class.java),
    DATE_TIME(TypeDefDateTime.INSTANCE, LocalDateTime::class.java),
    ZONED_DATE_TIME(null, ZonedDateTime::class.java),
    DURATION(TypeDefDuration.INSTANCE, Duration::class.java),
    OBJECT(TypeDefObject.INSTANCE, Object::class.java),
    VOID(TypeDefVoid.INSTANCE, Void::class.java),
    TERMINOLOGY_CODE(TypeDefTerminologyCode.INSTANCE, String::class.java),
    OBJECT_REF(TypeDefObjectRef.INSTANCE, ObjectRef::class.java);

    companion object {
        fun forExpressionType(expressionTypeDef: ExprTypeDef<*>?) =
            values().firstOrNull { Objects.equals(it.expressionTypeDef, expressionTypeDef) }
    }
}
