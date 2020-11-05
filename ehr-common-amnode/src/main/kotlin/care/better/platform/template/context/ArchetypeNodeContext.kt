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

package care.better.platform.template.context

import care.better.platform.template.AmNode

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class ArchetypeNodeContext(val archetypeRootNode: AmNode?, val parent: ArchetypeNodeContext?) {
    companion object {
        @JvmStatic
        fun root(): ArchetypeNodeContext = ArchetypeNodeContext(null, null)
    }

    private val references: MutableList<AmNodeReference> = mutableListOf()

    fun getReferences(): List<AmNodeReference> = references.toList()

    fun addReference(amNodeReference: AmNodeReference) {
        references.add(amNodeReference)
    }
}
