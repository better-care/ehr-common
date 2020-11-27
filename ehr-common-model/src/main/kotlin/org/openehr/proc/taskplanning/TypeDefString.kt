/* Copyright 2020-2025 Better Ltd (www.better.care)
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

import care.better.platform.annotation.Opened

/**
 * @author Primoz Delopst
 */

@Opened
class TypeDefString : ExprTypeDef<String>("String") {

    companion object {
        val INSTANCE: TypeDefString = TypeDefString()
    }

    override fun setTypeName(typeName: String?) {
        if ("String" != typeName) {
            throw UnsupportedOperationException("The type name has to be String")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefString{} ${super.toString()}"
}