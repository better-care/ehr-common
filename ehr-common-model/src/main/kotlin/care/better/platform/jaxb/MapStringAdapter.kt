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

package care.better.platform.jaxb

import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class MapStringAdapter : XmlAdapter<MapStringAdapter.StringAdaptedMap, MutableMap<String, String>>() {

    override fun unmarshal(stringAdaptedEntries: StringAdaptedMap): MutableMap<String, String> =
        stringAdaptedEntries.stringAdaptedEntry.associateTo(mutableMapOf(), { Pair(it.key, it.value) })

    override fun marshal(map: MutableMap<String, String>): StringAdaptedMap = StringAdaptedMap(map.entries.map { StringAdaptedEntry(it.key, it.value) })

    class StringAdaptedMap() {
        var stringAdaptedEntry: MutableList<StringAdaptedEntry> = mutableListOf()

        constructor(stringAdaptedEntry: List<StringAdaptedEntry>) : this() {
            this.stringAdaptedEntry.addAll(stringAdaptedEntry)
        }
    }

    class StringAdaptedEntry() {
        lateinit var key: String
        lateinit var value: String

        constructor(key: String, value: String) : this() {
            this.key = key
            this.value = value
        }
    }
}
