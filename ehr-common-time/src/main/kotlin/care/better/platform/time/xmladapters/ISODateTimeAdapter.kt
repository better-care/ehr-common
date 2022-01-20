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

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Bostjan Lah
 */
class ISODateTimeAdapter : XmlAdapter<String?, DateTime?>() {
    override fun unmarshal(v: String?): DateTime? {
        return if (v == null) null else DATE_TIME_PARSER.parseDateTime(v)
    }

    override fun marshal(v: DateTime?): String? {
        return if (v == null) null else DATE_TIME_FORMATTER.print(v)
    }

    companion object {
        private val DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime().withOffsetParsed()
        private val DATE_TIME_PARSER = ISODateTimeFormat.dateTimeParser().withOffsetParsed()
    }
}