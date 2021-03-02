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

package care.better.platform

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
abstract class ModelVersion(vararg versions: Version<*>?) {

    private var versionMap: MutableMap<Class<*>, Version<*>> = linkedMapOf()

    init {
        if (versions.isNullOrEmpty() || versions.all { it == null }) {
            throw IllegalArgumentException("Must specify at least one valid version.")
        }
        versions.asSequence().filterNotNull().forEach { versionMap[it::class.java] = it }
    }

    companion object {
        const val IDENTIFIER: String = "MVer"
    }

    fun getVersionMap(): Map<Class<*>, Version<*>> = versionMap

    @Suppress("UNCHECKED_CAST")
    fun <T : Version<*>> getVersion(versionClass: Class<T>): T? = versionMap[versionClass] as T?

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Version<*>> compareTo(other: T): Int {
        val versionClass = other::class.java
        if (versionMap.containsKey(versionClass)) {
            val version: Version<T> = requireNotNull(versionMap[versionClass]) as Version<T>
            return version.compareTo(other)
        }
        throw IllegalArgumentException("Missing Version ${versionClass::class.java.simpleName} from ${this::class.java.simpleName}.")
    }

    fun getIdentifier(): String = IDENTIFIER

    override fun toString(): String = versionMap.values.joinToString(",") { it.getVersion() }
}
