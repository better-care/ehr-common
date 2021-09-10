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

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrLocalDate
import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDateAdapter : XmlAdapter<String?, OpenEhrLocalDate?>() {

    override fun unmarshal(v: String?): OpenEhrLocalDate? {
        return v?.let { value ->
            OpenEhrDateTimeFormatter.ofOptionalLocalDate(false)
                .parseDate(value) { temporal, precisionField, context -> OpenEhrLocalDate.from(temporal, precisionField, context) }
        }
    }

    override fun marshal(v: OpenEhrLocalDate?): String? {
        return v?.let { localDate -> OpenEhrDateTimeFormatter.ofOptionalLocalDate().format(localDate) }
    }
}