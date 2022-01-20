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

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Bostjan Lah
 */
class ISOOffsetDateTimeAdapter : XmlAdapter<String?, OffsetDateTime?>() {
    override fun unmarshal(v: String?): OffsetDateTime? {
        return if (v == null) null else OffsetDateTime.parse(v, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    override fun marshal(v: OffsetDateTime?): String? {
        return if (v == null) null else DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(v)
    }
}