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

import org.openehr.rm.common.Locatable
import org.openehr.rm.common.OriginalVersion
import org.openehr.rm.common.VersionedObject
import org.openehr.rm.composition.Composition
import java.io.Serializable

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class VersionedDataDto : Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var version: OriginalVersion? = null
    var versionedObject: VersionedObject? = null
    var locatable: Locatable? = null
    val composition: Composition? get() = locatable as Composition?
}
