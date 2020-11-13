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

@file:Suppress("unused")

package care.better.platform.utils

import org.joda.time.*
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.format.ISOPeriodFormat
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.ObjectRef
import org.openehr.base.basetypes.ObjectVersionId
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.common.Link
import org.openehr.rm.common.PartyIdentified
import org.openehr.rm.datatypes.*

/**
 * @author Primoz Delopst
 */

/**
 * Creates a [DvText] from a string
 *
 * @param value input value
 * @return [DvText] object
 */
fun DvText.Companion.create(value: String): DvText = DvText().apply { this.value = value }

/**
 * Creates a [DvCodedText] from terminology id, code and value
 *
 * @param terminology terminology id
 * @param code        code
 * @param value       value
 * @return [DvCodedText] object
 */
fun DvCodedText.Companion.create(terminology: String, code: String, value: String): DvCodedText =
        DvCodedText().apply {
            this.definingCode = CodePhrase.create(terminology, code)
            this.value = value
        }

/**
 * Creates a [DvCodedText] with openEHR terminology, code and value
 *
 * @param code  code
 * @param value value
 * @return [DvCodedText] object
 */
fun DvCodedText.Companion.createLocal(code: String, value: String): DvCodedText = create("local", code, value)

fun DvCodedText.Companion.createOpenEhr(code: String, value: String): DvCodedText = create("openehr", code, value)

/**
 * Creates a [CodePhrase] from terminology id and code
 *
 * @param terminology terminology id
 * @param code        code
 * @return [CodePhrase] object
 */
fun CodePhrase.Companion.create(terminology: String, code: String): CodePhrase =
        CodePhrase().apply {
            this.terminologyId = TerminologyId().apply { this.value = terminology }
            this.codeString = code
        }

/**
 * Creates [DvOrdinal] from value and symbol
 *
 * @param value  numeric value
 * @param symbol [DvCodedText] symbol
 * @return [DvOrdinal] object
 */
fun DvOrdinal.Companion.create(value: Int, symbol: DvCodedText): DvOrdinal =
        DvOrdinal().apply {
            this.value = value
            this.symbol = symbol
        }

/**
 * Creates [DvQuantity] from a magnitude (numeric value), unit string and precision. Precision can be null.
 *
 * @param magnitude magnitude
 * @param units     unit
 * @param precision precision
 * @return [DvQuantity] object
 */
fun DvQuantity.Companion.create(magnitude: Double, units: String, precision: Int?): DvQuantity =
        DvQuantity().apply {
            this.magnitude = magnitude
            this.precision = precision
            this.units = units
        }

/**
 * Creates [DvQuantity] from a magnitude (numeric value) and unit string. Precision is not set.
 *
 * @param magnitude magnitude
 * @param units     unit
 * @return [DvQuantity] object
 */
fun DvQuantity.Companion.create(magnitude: Double, units: String): DvQuantity = create(magnitude, units, null)


/**
 * Creates a [PartyIdentified] from a party name
 *
 * @param name party name
 * @return [PartyIdentified]
 */
fun PartyIdentified.Companion.create(name: String): PartyIdentified = PartyIdentified().apply { this.name = name }


/**
 * Converts a ReadablePeriod to [DvDuration]
 *
 * @param period [ReadablePeriod]
 * @return [DvDuration] object
 */
fun DvDuration.Companion.create(period: ReadablePeriod): DvDuration = DvDuration().apply { this.value = ISOPeriodFormat.standard().print(period) }


/**
 * Converts a string duration to [DvDuration]
 *
 * @param value [String] duration in standard ISO format - PyYmMwWdDThHmMsS.
 * @return [DvDuration] object
 */
fun DvDuration.Companion.create(value: String?): DvDuration? =
        value?.let {
            DvDuration().apply {
                ISOPeriodFormat.standard().parsePeriod(value)
                this.value = value
            }
        }

/**
 * Converts [DvDuration] to Joda Period
 *
 * @return [Period]
 */
fun DvDuration.toPeriod(): Period = ISOPeriodFormat.standard().parsePeriod(requireNotNull(value))


/**
 * Converts duration string value to [Period]
 *
 * @param durationValue duration string
 * @return [Period]
 */
fun DvDuration.Companion.toPeriod(durationValue: String): Period = ISOPeriodFormat.standard().parsePeriod(durationValue)

/**
 * Converts Joda DateTime to DV_DATETIME
 *
 * @param dateTime Joda DateTime
 * @return DV_DATETIME object
 */
fun DvDateTime.Companion.create(dateTime: DateTime): DvDateTime = DvDateTime().apply { this.value = ISODateTimeFormat.dateTime().print(dateTime) }

/**
 * Converts [LocalDate] to [DvDate]
 *
 * @param date [LocalDate]
 * @return [DvDate] object
 */
fun DvDate.Companion.create(date: LocalDate): DvDate = DvDate().apply { this.value = ISODateTimeFormat.date().print(date) }

/**
 * Converts [LocalTime] to [DvTime]
 *
 * @param time [LocalTime]
 * @return [DvTime] object
 */
fun DvTime.Companion.create(time: LocalTime): DvTime = DvTime().apply { this.value = ISODateTimeFormat.time().print(time) }

/**
 * Converts DV_DATETIME to Joda DateTime
 *
 * @return Joda DateTime
 */
fun DvDateTime.toDateTime(): DateTime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(requireNotNull(value))

/**
 * Converts string date time to [DateTime]
 *
 * @param dateTimeValue string value (ISO format)
 * @return [DateTime]
 */
fun DvDateTime.Companion.toDateTime(dateTimeValue: String): DateTime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(dateTimeValue)

/**
 * Converts [DvTime] to [LocalTime]
 *
 * @return [LocalTime]
 */
fun DvTime.toLocalTime(): LocalTime =
        with(requireNotNull(value)) {
            val timeIndex = this.indexOf('T')
            ISODateTimeFormat.timeParser().withOffsetParsed().parseLocalTime(
                    if (timeIndex == -1)
                        this
                    else
                        this.substring(timeIndex + 1))
        }

/**
 * Converts [DvDate] to [LocalDate]
 *
 * @return [LocalDate]
 */
fun DvDate.toLocalDate(): LocalDate = ISODateTimeFormat.dateOptionalTimeParser().withOffsetParsed().parseLocalDate(requireNotNull(value))

/**
 * Converts a boolean to [DvBoolean]
 *
 * @param value boolean value
 * @return [DvBoolean] object
 */
fun DvBoolean.Companion.create(value: Boolean): DvBoolean = DvBoolean().apply { this.value = value }

/**
 * Creates [DvEhrUri].
 *
 * @param ehrUid         ehr uid
 * @param compositionUid composition uid - this can be full uid (xxx::system_id::version) or just versioned uid (xxx). If full path is specified then link
 * points to a specific composition version, otherwise it points to the last version.
 * @return [DvEhrUri] object
 */
fun DvEhrUri.Companion.create(ehrUid: String, compositionUid: String): DvEhrUri = create(ehrUid, compositionUid, null)

/**
 * Creates [DvEhrUri].
 *
 * @param ehrUid         ehr uid
 * @param compositionUid composition uid - this can be full uid (xxx::system_id::version) or just versioned uid (xxx). If full path is specified then link
 * points to a specific composition version, otherwise it points to the last version.
 * @param path           RM path to an element within a composition
 * @return [DvEhrUri] object
 */
fun DvEhrUri.Companion.create(ehrUid: String, compositionUid: String, path: String?): DvEhrUri =
        DvEhrUri().apply {
            this.value = "ehr://$ehrUid/$compositionUid${path?.let { if (it.startsWith("/")) it else "/$it" } ?: ""}"
        }

/**
 * Creates a person [ObjectRef].
 *
 * @param uid       uid
 * @param namespace namespace
 * @return [ObjectRef] object
 */
fun ObjectRef.Companion.create(uid: String, namespace: String): ObjectRef = create("PERSON", uid, namespace)

/**
 * Creates an [ObjectRef].
 *
 * @param type      type
 * @param uid       uid
 * @param namespace namespace
 * @return [ObjectRef] object
 */
fun ObjectRef.Companion.create(type: String, uid: String, namespace: String): ObjectRef =
        ObjectRef().apply {
            this.id = ObjectVersionId.create(uid)
            this.namespace = namespace
            this.type = type
        }

/**
 * Creates a [HierObjectId]
 *
 * @param uid uid
 * @return [HierObjectId]] object
 */
fun HierObjectId.Companion.create(uid: String): HierObjectId = HierObjectId().apply { this.value = uid }

/**
 * Creates an [ObjectVersionId]
 *
 * @param uid uid
 * @return [ObjectVersionId] object
 */
fun ObjectVersionId.Companion.create(uid: String): ObjectVersionId = ObjectVersionId().apply { this.value = uid }

/**
 * Creates an [ObjectVersionId] from versioned object id, system id and version number.
 *
 * @param versionedObjectUid versioned uid
 * @param systemId           system id
 * @param version            version
 * @return [ObjectVersionId] object
 */
fun ObjectVersionId.Companion.create(versionedObjectUid: String, systemId: String, version: Int): ObjectVersionId =
        create("$versionedObjectUid::$systemId::$version")

/**
 * Gets language [CodePhrase]
 *
 * @param languageCode ISO language code (ISO_639-1)
 * @return [CodePhrase] object
 */
fun CodePhrase.Companion.createLanguagePhrase(languageCode: String): CodePhrase = create("ISO_639-1", languageCode)

/**
 * Gets territory [CodePhrase]
 *
 * @param territoryCode ISO territory code (ISO_3166-1)
 * @return [CodePhrase] object
 */
fun CodePhrase.Companion.createTerritoryPhrase(territoryCode: String): CodePhrase = create("ISO_3166-1", territoryCode)

/**
 * Gets encoding [CodePhrase]
 *
 * @param encodingCode encoding code (IANA character sets)
 * @return [CodePhrase] object
 */
fun CodePhrase.Companion.createEncodingPhrase(encodingCode: String): CodePhrase = create("IANA_character-sets", encodingCode)

/**
 * Creates [DvParsable] from a value and formalism.
 *
 * @param value     parsable content
 * @param formalism parsable formalism
 * @return [DvParsable] object
 */
fun DvParsable.Companion.create(value: String, formalism: String): DvParsable =
        DvParsable().apply {
            this.formalism = formalism
            this.value = value
        }

/**
 * Creates a name suffix suitable for use in LINKs (i.e. /items[at0001,&gt;&gt;'Order #2'&lt;&lt;]/...)
 *
 * @param name  name part of suffix
 * @param index element index (0-based)
 * @return complete suffix to be placed after node id
 */
fun Link.Companion.getNameSuffix(name: String, index: Int): String = '\''.toString() + quote(name) + (if (index > 0) " #" + (index + 1) else "") + '\''

private fun quote(parameter: String): String = parameter.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'")
