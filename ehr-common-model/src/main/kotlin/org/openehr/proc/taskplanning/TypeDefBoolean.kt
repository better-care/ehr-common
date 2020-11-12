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

/**
 * @author Primoz Delopst
 */

class TypeDefBoolean : ExprTypeDef<Boolean>("Boolean") {

    companion object {
        val INSTANCE: TypeDefBoolean = TypeDefBoolean()
    }

    override fun setTypeName(typeName: String?) {
        if ("Boolean" != typeName) {
            throw UnsupportedOperationException("The type name has to be Boolean")
        }
        super.setTypeName(typeName)
    }


    override fun toString(): String = "TypeDefBoolean{} ${super.toString()}"
}