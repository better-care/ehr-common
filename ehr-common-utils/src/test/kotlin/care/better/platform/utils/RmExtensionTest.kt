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

package care.better.platform.utils

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.joda.time.*
import org.joda.time.format.ISODateTimeFormat
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.ObjectRef
import org.openehr.base.basetypes.ObjectVersionId
import org.openehr.rm.common.Link
import org.openehr.rm.common.PartyIdentified
import org.openehr.rm.datatypes.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class RmExtensionTest {

    @Test
    fun dvText() {
        val dvText = DvText("a")
        evaluateDvText(dvText, "a")
    }

    private fun evaluateDvText(dvText: DvText, value: String?) {
        assertThat(dvText.value).isEqualTo(value)
        assertThat(dvText.encoding).isNull()
        assertThat(dvText.formatting).isNull()
        assertThat(dvText.hyperlink).isNull()
        assertThat(dvText.language).isNull()
        assertThat(dvText.mappings).isEmpty()
    }

    @Test
    fun partyIdentified() {
        val partyIdentified: PartyIdentified = PartyIdentified.forName("a")
        assertThat(partyIdentified.name).isEqualTo("a")
        assertThat(partyIdentified.externalRef).isNull()
        assertThat(partyIdentified.identifiers).isEmpty()
    }

    @Test
    fun codedText() {
        val codedText: DvCodedText = DvCodedText.create("openehr", "1", "value")
        evaluateDvCodedText(codedText, "openehr", "1", "value")
    }

    private fun evaluateDvCodedText(
            codedText: DvCodedText,
            terminology: String,
            code: String,
            value: String?) {
        assertThat(codedText.definingCode?.codeString).isEqualTo(code)
        assertThat(codedText.definingCode?.terminologyId?.value).isEqualTo(terminology)
        evaluateDvText(codedText, value)
    }

    @Test
    fun ordinal() {
        val ordinal = DvOrdinal(1, DvCodedText.create("openehr", "mm", "mm"))
        evaluateDvCodedText(ordinal.symbol!!, "openehr", "mm", "mm")
        assertThat(ordinal.value).isEqualTo(1)
        assertThat(ordinal.normalRange).isNull()
        assertThat(ordinal.normalStatus).isNull()
        assertThat(ordinal.otherReferenceRanges).isEmpty()
    }

    @Test
    fun quantity() {
        val quantity = DvQuantity(1.0, "mm")
        assertThat(quantity.magnitude).isEqualTo(1.0)
        assertThat(quantity.units).isEqualTo("mm")
        assertThat(quantity.precision).isNull()
        assertThat(quantity.normalRange).isNull()
        assertThat(quantity.normalStatus).isNull()
        assertThat(quantity.otherReferenceRanges).isEmpty()
        val quantity1 = DvQuantity(1.0, "mm", 2)
        assertThat(quantity1.magnitude).isEqualTo(1.0)
        assertThat(quantity1.units).isEqualTo("mm")
        assertThat(quantity1.precision).isEqualTo(2)
        assertThat(quantity1.normalRange).isNull()
        assertThat(quantity1.normalStatus).isNull()
        assertThat(quantity1.otherReferenceRanges).isEmpty()
    }

    @Test
    fun duration() {
        val period = Period(DateTime(2011, 1, 1, 1, 0, 0), DateTime(2011, 1, 1, 2, 0, 0))
        val duration: DvDuration = DvDuration.create(period)
        assertThat(duration.value).isEqualTo("PT1H")
        assertThat(period).isEqualTo(duration.toPeriod())
        val period1 = Period(DateTime(2011, 1, 1, 1, 0, 0), DateTime(2011, 1, 2, 2, 0, 0))
        val duration1: DvDuration = DvDuration.create(period1)
        assertThat(duration1.value).isEqualTo("P1DT1H")
        assertThat(period1).isEqualTo(duration1.toPeriod())
        assertThat(DvDuration.create("P1YT1H")?.value).isEqualTo("P1YT1H")
        assertThat(DvDuration.create(null as String?)).isNull()
    }

    @Test
    fun invalidDuration() {
        assertThatThrownBy { DvDuration.create("P1YX1H") }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun dateTime() {
        val dateTime = DateTime(2011, 1, 1, 1, 0, 0, DateTimeZone.UTC)
        val dvDateTime: DvDateTime = DvDateTime.create(dateTime)
        assertThat(dvDateTime.value).isEqualTo("2011-01-01T01:00:00.000Z")
        assertThat(dvDateTime.toDateTime().withZone(DateTimeZone.UTC)).isEqualTo(dateTime)
        assertThat(DvDateTime.toDateTime("2011-01-01T01:00:00.000Z").withZone(DateTimeZone.UTC)).isEqualTo(dateTime)
        val dateTime1 = DateTime(2011, 1, 1, 1, 0, 0, DateTimeZone.forOffsetHours(1))
        assertThat(DvDateTime.toDateTime("2011-01-01T01:00+01:00")).isEqualTo(dateTime1)
    }

    @Test
    fun localDate() {
        val date = LocalDate(2012, 3, 17)
        val dvDate: DvDate = DvDate.create(date)
        assertThat(dvDate.value).isEqualTo("2012-03-17")
        assertThat(dvDate.toJodaLocalDate()).isEqualTo(date)
        dvDate.value = "2011-01-01T01:00:00.000Z"
        assertThat(dvDate.toJodaLocalDate()).isEqualTo(LocalDate(2011, 1, 1))
        dvDate.value = "2011-01-01T01:00:00.000"
        assertThat(dvDate.toJodaLocalDate()).isEqualTo(LocalDate(2011, 1, 1))
    }

    @Test
    fun localTime() {
        val time = LocalTime(13, 39, 17, 776)
        val dvTime: DvTime = DvTime.create(time)
        assertThat(dvTime.value).isEqualTo("13:39:17.776")
        assertThat(dvTime.toJodaLocalTime()).isEqualTo(time)
        dvTime.value = "13:39:17.776+01:00"
        assertThat(dvTime.toJodaLocalTime()).isEqualTo(time)
        dvTime.value = "13:39:17"
        assertThat(dvTime.toJodaLocalTime()).isEqualTo(LocalTime(13, 39, 17))
    }

    @Test
    fun localTimeFromDateTime() {
        val time = DvTime().apply { this.value = "2011-01-01T13:39:17.776Z" }
        assertThat(time.toJodaLocalTime()).isEqualTo(LocalTime(13, 39, 17, 776))
    }

    @Test
    fun parsable() {
        val parsable = DvParsable("value", "formalism")
        assertThat(parsable.value).isEqualTo("value")
        assertThat(parsable.formalism).isEqualTo("formalism")
    }


    @Test
    fun booleanConversion() {
        val dvBooleanT: DvBoolean = DvBoolean.create(true)
        assertThat(dvBooleanT.value).isTrue
        val dvBooleanF: DvBoolean = DvBoolean.create(false)
        assertThat(dvBooleanF.value).isFalse
    }

    @Test
    fun ehrUriNoPath() {
        val ehrUri1: DvEhrUri = DvEhrUri.create("ehrUid", "compositionUid")
        assertThat(ehrUri1.value).isEqualTo("ehr://ehrUid/compositionUid")
        val ehrUri2: DvEhrUri = DvEhrUri.create("ehrUid", "compositionUid", null)
        assertThat(ehrUri2.value).isEqualTo("ehr://ehrUid/compositionUid")
    }

    @Test
    fun ehrUriWithPath() {
        val ehrUri: DvEhrUri = DvEhrUri.create("ehrUid", "compositionUid", "content[at0001]/name/value")
        assertThat(ehrUri.value).isEqualTo("ehr://ehrUid/compositionUid/content[at0001]/name/value")
    }

    @Test
    fun ehrObjectRef() {
        val ehrObjectRef: ObjectRef = ObjectRef.createPerson("ehrUid", "ns")
        assertThat(ehrObjectRef.id?.value).isEqualTo("ehrUid")
        assertThat(ehrObjectRef.namespace).isEqualTo("ns")
    }


    @Test
    fun objectVersionId() {
        val uid = ObjectVersionId("uid")
        assertThat(uid.value).isEqualTo("uid")
    }

    @Test
    fun objectVersionIdFull() {
        val uid: ObjectVersionId = ObjectVersionId.create("uid", "pek.marand.si", 12)
        assertThat(uid.value).isEqualTo("uid::pek.marand.si::12")
    }

    @Test
    fun hierObjectId() {
        val uid = HierObjectId("uid")
        assertThat(uid.value).isEqualTo("uid")
    }

    @Test
    fun language() {
        val cp: CodePhrase = CodePhrase.createLanguagePhrase("sl")
        assertThat(cp.terminologyId?.value).isEqualTo("ISO_639-1")
        assertThat(cp.codeString).isEqualTo("sl")
    }

    @Test
    fun territory() {
        val cp: CodePhrase = CodePhrase.createTerritoryPhrase("SI")
        assertThat(cp.terminologyId?.value).isEqualTo("ISO_3166-1")
        assertThat(cp.codeString).isEqualTo("SI")
    }

    @Test
    fun encoding() {
        val cp: CodePhrase = CodePhrase.createEncodingPhrase("UTF-8")
        assertThat(cp.terminologyId?.value).isEqualTo("IANA_character-sets")
        assertThat(cp.codeString).isEqualTo("UTF-8")
    }

    @Test
    fun toDvDateTimeZone() {
        val formatter = ISODateTimeFormat.dateTime().withOffsetParsed()
        val dateTime1 = DateTime(2015, 1, 1, 12, 0, DateTimeZone.UTC)
        val dvDateTime1: DvDateTime = DvDateTime.create(dateTime1)
        assertThat(dvDateTime1.value).isEqualTo(formatter.print(dateTime1))
        val dateTime2 = DateTime(2015, 1, 1, 12, 0, DateTimeZone.forOffsetHours(7))
        val dvDateTime2: DvDateTime = DvDateTime.create(dateTime2)
        assertThat(dvDateTime2.value).isEqualTo(formatter.print(dateTime2))
    }

    @Test
    fun fromDvDateTimeZone() {
        val dateTime1 = DateTime(2015, 1, 1, 12, 0, DateTimeZone.UTC)
        val dvDateTime1: DvDateTime = DvDateTime.create(dateTime1)
        val dateTime2: DateTime = dvDateTime1.toDateTime()
        assertThat(dateTime2.zone).isEqualTo(dateTime1.zone)
    }

    @Test
    fun jsr310DateTimeUTC() {
        val zonedDateTime1 = ZonedDateTime.of(2015, 1, 1, 12, 0, 0, 1, ZoneId.of("Z"))
        val dvDateTime1: DvDateTime = DvDateTime.create(zonedDateTime1)
        assertThat(dvDateTime1.value).isEqualTo("2015-01-01T12:00:00.000000001Z")
        val zonedDateTime2 = dvDateTime1.toZonedDateTime()
        assertThat(zonedDateTime2.zone).isEqualTo(zonedDateTime1.zone)
        assertThat(zonedDateTime2).isEqualTo(zonedDateTime1)
        val offsetDateTime1 = OffsetDateTime.of(2015, 1, 1, 12, 0, 0, 1, ZoneOffset.UTC)
        val dvDateTime2: DvDateTime = DvDateTime.create(offsetDateTime1)
        assertThat(dvDateTime2.value).isEqualTo("2015-01-01T12:00:00.000000001Z")
        val offsetDateTime2 = dvDateTime2.toOffsetDateTime()
        assertThat(offsetDateTime2.offset).isEqualTo(offsetDateTime1.offset)
        assertThat(offsetDateTime2).isEqualTo(offsetDateTime1)
    }

    @Test
    fun jsr310DateTime() {
        val zonedDateTime1 = ZonedDateTime.of(2015, 1, 1, 12, 0, 0, 1, ZoneId.systemDefault())
        val dvDateTime1: DvDateTime = DvDateTime.create(zonedDateTime1)
        assertThat(dvDateTime1.value).isEqualTo("2015-01-01T12:00:00.000000001+01:00")
        val zonedDateTime2 = dvDateTime1.toZonedDateTime()
        assertThat(zonedDateTime2.toOffsetDateTime()).isEqualTo(zonedDateTime1.toOffsetDateTime())
        val offsetDateTime1 = OffsetDateTime.of(2015, 1, 1, 12, 0, 0, 1, ZoneOffset.ofHours(1))
        val dvDateTime2: DvDateTime = DvDateTime.create(offsetDateTime1)
        assertThat(dvDateTime2.value).isEqualTo("2015-01-01T12:00:00.000000001+01:00")
        val offsetDateTime2 = dvDateTime2.toOffsetDateTime()
        assertThat(offsetDateTime2.offset).isEqualTo(offsetDateTime1.offset)
        assertThat(offsetDateTime2).isEqualTo(offsetDateTime1)
        val dvDateTime3 = DvDateTime()
        dvDateTime3.value = "2015-01-01T12:00:00.000000001"
        assertThat(dvDateTime3.toZonedDateTime()).isNotNull
        val dvDateTime4 = DvDateTime()
        dvDateTime4.value = "2015-01-01T12:00:00"
        assertThat(dvDateTime4.toZonedDateTime()).isNotNull
        assertThat(dvDateTime4.toOffsetDateTime()).isNotNull
    }

    @Test
    fun jsr310LocalDate() {
        val date1 = LocalDate.of(2015, 1, 1)
        val dvDate1: DvDate = DvDate.create(date1)
        assertThat(dvDate1.value).isEqualTo("2015-01-01")
        val date2 = dvDate1.toLocalDate()
        assertThat(date1).isEqualTo(date2)
    }

    @Test
    fun jsr310LocalTime() {
        val time1 = LocalTime.of(11, 19, 30)
        val dvTime1: DvTime = DvTime.create(time1)
        assertThat(dvTime1.value).isEqualTo("11:19:30")
        val time2 = dvTime1.toLocalTime()
        assertThat(time1).isEqualTo(time2)
    }

    @Test
    fun nameSuffix() {
        assertThat(Link.getNameSuffix("Hello", 0)).isEqualTo("'Hello'")
        assertThat(Link.getNameSuffix("Hello", 1)).isEqualTo("'Hello #2'")
        assertThat(Link.getNameSuffix("Hell'o", 1)).isEqualTo("'Hell\\'o #2'")
    }
}
