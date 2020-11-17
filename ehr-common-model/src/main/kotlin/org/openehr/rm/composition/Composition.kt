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

package org.openehr.rm.composition

import care.better.platform.annotation.Required
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class Composition : Locatable() {
    @Required
    var language: CodePhrase? = null

    @Required
    var territory: CodePhrase? = null

    @Required
    var category: DvCodedText? = null

    @Required
    var composer: PartyProxy? = null
    var context: EventContext? = null
    var content: MutableList<ContentItem> = mutableListOf()
}