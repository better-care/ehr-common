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
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Context used to hold [List] of [PathSegment].
 *
 * @constructor Creates new instance of [ContextWithPath]
 */
open class ContextWithPath {
    private val pathSegments: MutableList<PathSegment> = mutableListOf()

    /**
     * Returns [List] of [PathSegment] associated with this [ContextWithPath].
     *
     * @return [List] of [PathSegment] associated with this [ContextWithPath]
     */
    fun getPathSegments(): List<PathSegment> = pathSegments

    /**
     * Returns number of [PathSegment] that are associated with this context.
     *
     * @return Number of [PathSegment] that are associated with this context
     */
    fun getSegmentsSize(): Int = pathSegments.size

    /**
     * Adds [PathSegment] to this [ContextWithPath].
     *
     * @param pathSegment [PathSegment]
     */
    fun addPathSegment(pathSegment: PathSegment) {
        pathSegments.add(pathSegment)
    }

    /**
     * Removes last added [PathSegment] from this [PathSegment] and returns removed [PathSegment].
     *
     * @return Removed [PathSegment] if removed, otherwise, return null
     */
    fun removeLastPathSegment(): PathSegment? =
        if (pathSegments.isEmpty())
            null
        else
            pathSegments.removeAt(pathSegments.size - 1)

    /**
     * Returns names of the [PathSegment] that are  associated with this [ContextWithPath].
     *
     * @return Names of the [PathSegment] that are  associated with this [ContextWithPath]
     */
    fun getPathNames(): List<String?> {
        val names: MutableList<String?> = ArrayList()
        for (pathSegment in pathSegments) {
            names.add(pathSegment.name)
        }

        val iterator = names.listIterator(names.size)
        while (iterator.hasPrevious()) {
            if (iterator.previous() == null)
                iterator.remove()
            else
                break
        }

        return names.toList()
    }
}
