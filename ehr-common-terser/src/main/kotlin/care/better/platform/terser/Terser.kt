/* Copyright 2026 Better Ltd (www.better.care)
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

package care.better.platform.terser

import care.better.openehr.rm.RmObject

/**
 * @author Primoz Delopst
 * @since 4.3.1
 *
 * Singleton that provides access to RM objects using AQL paths.
 */
object Terser {

    /**
     * Extracts and returns [List] of values at the given AQL path.
     *
     * @param rmObject RM object
     * @param path AQL path [String]
     * @return [List] of extracted values
     */
    fun getValues(rmObject: RmObject, path: String): List<Any> = RmTerser(rmObject).getValues(path)

    /**
     * Extracts and returns a single value at the given AQL path, or null if not found.
     *
     * @param rmObject RM object
     * @param path AQL path [String]
     * @return Extracted value or null
     * @throws IllegalArgumentException if multiple values are found at the given path
     */
    fun getValue(rmObject: RmObject, path: String): Any? = RmTerser(rmObject).getValue(path)

    /**
     * Sets the value at the given AQL path on all matching parent nodes.
     *
     * @param rmObject RM object
     * @param path AQL path [String]
     * @param value Value to set (or null to clear)
     */
    fun setValue(rmObject: RmObject, path: String, value: Any?) = RmTerser(rmObject).setValue(path, value)

    /**
     * Adds a value to a collection at the given AQL path on all matching parent nodes.
     *
     * @param rmObject RM object
     * @param path AQL path [String]
     * @param value Value to add
     */
    fun addValue(rmObject: RmObject, path: String, value: Any?) = RmTerser(rmObject).addValue(path, value)

    /**
     * Removes values at the given AQL path.
     *
     * @param rmObject RM object
     * @param path AQL path [String]
     */
    fun removeValue(rmObject: RmObject, path: String) = RmTerser(rmObject).removeValue(path)
}
