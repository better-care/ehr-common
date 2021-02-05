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

package care.better.platform.path

import java.util.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class ContextWithPath {
    private val pathSegments: MutableList<PathSegment> = mutableListOf()

    fun getPathSegments(): List<PathSegment> = pathSegments

    fun getSegmentsSize(): Int = pathSegments.size

    fun addPathSegment(pathSegment: PathSegment) {
        pathSegments.add(pathSegment)
    }

    fun removeLastPathSegment(): PathSegment? =
        if (pathSegments.isEmpty())
            null
        else
            pathSegments.removeAt(pathSegments.size - 1)

    fun getPathNames(): List<String?> {
        val names: MutableList<String?> = ArrayList()
        for (pathSegment in pathSegments) {
            names.add(pathSegment.name)
        }
        removeTrailingNulls(names)
        return names.toList()
    }

    private fun removeTrailingNulls(names: MutableList<String?>) {
        val iterator = names.listIterator(names.size)
        while (iterator.hasPrevious()) {
            if (iterator.previous() == null)
                iterator.remove()
            else
                break
        }
    }
}
