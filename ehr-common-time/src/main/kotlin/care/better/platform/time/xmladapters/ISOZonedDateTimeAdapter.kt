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

package care.better.platform.time.xmladapters

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Bostjan Lah
 */
class ISOZonedDateTimeAdapter : XmlAdapter<String?, ZonedDateTime?>() {
    @Throws(Exception::class)
    override fun unmarshal(v: String?): ZonedDateTime? {
        return if (v == null) null else ZonedDateTime.parse(v, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    @Throws(Exception::class)
    override fun marshal(v: ZonedDateTime?): String? {
        return if (v == null) null else DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(v)
    }
}