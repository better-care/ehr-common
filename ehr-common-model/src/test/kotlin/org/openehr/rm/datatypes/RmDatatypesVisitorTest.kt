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

package org.openehr.rm.datatypes

import care.better.platform.visitor.RmVisitorTest
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.TerminologyId

/**
 * Tests visitor implementation for org.openehr.rm.datatypes package classes
 */
class RmDatatypesVisitorTest : RmVisitorTest() {

    @Test
    fun `test CodePhrase visit`() {
        val instance = CodePhrase()
        instance.terminologyId = TerminologyId("openehr")
        instance.codeString = "123"
        instance.preferredTerm = "test term"

        validateVisit(instance)
    }

    @Test
    fun `test DvAmount visit`() {
        val instance = DvAmount()
        instance.accuracy = 0.5f
        instance.accuracyIsPercent = true

        validateVisit(instance)
    }

    @Test
    fun `test DvBoolean visit`() {
        val instance = DvBoolean(true)

        validateVisit(instance)
    }

    @Test
    fun `test DvCodedText visit`() {
        val instance = DvCodedText()
        instance.value = "test value"
        instance.definingCode = CodePhrase(TerminologyId("local"), "at0001")

        validateVisit(instance)
    }

    @Test
    fun `test DvCount visit`() {
        val instance = DvCount(5)

        validateVisit(instance)
    }

    @Test
    fun `test DvDate visit`() {
        val instance = DvDate()
        instance.value = "2024-01-01"

        validateVisit(instance)
    }

    @Test
    fun `test DvDateTime visit`() {
        val instance = DvDateTime()
        instance.value = "2024-01-01T00:00:00Z"

        validateVisit(instance)
    }

    @Test
    fun `test DvDuration visit`() {
        val instance = DvDuration()
        instance.value = "PT1H"

        validateVisit(instance)
    }

    @Test
    fun `test DvEhrUri visit`() {
        val instance = DvEhrUri()
        instance.value = "ehr://test/path"

        validateVisit(instance)
    }

    @Test
    fun `test DvGeneralTimeSpecification visit`() {
        val instance = DvGeneralTimeSpecification(DvParsable("test time spec", "plain/text"))
        validateVisit(instance)
    }

    @Test
    fun `test DvIdentifier visit`() {
        val instance = DvIdentifier()
        instance.id = "test-id"

        validateVisit(instance)
    }

    @Test
    fun `test DvInterval visit`() {
        val instance = DvInterval()
        instance.lower = DvQuantity(10.0, "kg")
        instance.upper = DvQuantity(20.0, "kg")
        instance.lowerIncluded = true
        instance.upperIncluded = true
        instance.lowerUnbounded = false
        instance.upperUnbounded = false

        validateVisit(instance)
    }

    @Test
    fun `test DvMultimedia visit`() {
        val instance = DvMultimedia()
        instance.mediaType = CodePhrase(TerminologyId("IANA_media-types"), "text/plain")
        instance.alternateText = "test alternate text"
        instance.size = 1024

        validateVisit(instance)
    }

    @Test
    fun `test DvOrdinal visit`() {
        val instance = DvOrdinal()
        instance.value = 1
        instance.symbol = DvCodedText(CodePhrase(TerminologyId("local"), "at0001"), "mild")

        validateVisit(instance)
    }

    @Test
    fun `test DvParagraph visit`() {
        val instance = DvParagraph()
        instance.items = mutableListOf(DvText("test paragraph text"))

        validateVisit(instance)
    }

    @Test
    fun `test DvParsable visit`() {
        val instance = DvParsable()
        instance.value = "test value"
        instance.formalism = "text/plain"

        validateVisit(instance)
    }

    @Test
    fun `test DvPeriodicTimeSpecification visit`() {
        val instance = DvPeriodicTimeSpecification(DvParsable("test periodic time spec", "text/plain"))
        validateVisit(instance)
    }

    @Test
    fun `test DvProportion visit`() {
        val instance = DvProportion()
        instance.numerator = 1.0f
        instance.denominator = 2.0f
        instance.type = 0
        instance.precision = 2

        validateVisit(instance)
    }

    @Test
    fun `test DvQuantity visit`() {
        val instance = DvQuantity()
        instance.magnitude = 10.0
        instance.units = "kg"
        instance.precision = 1

        validateVisit(instance)
    }

    @Test
    fun `test DvScale visit`() {
        val instance = DvScale()
        instance.value = 5.0
        instance.symbol = DvCodedText(CodePhrase(TerminologyId("local"), "at0001"), "moderate")

        validateVisit(instance)
    }

    @Test
    fun `test DvState visit`() {
        val instance = DvState()
        instance.value = DvCodedText(CodePhrase(TerminologyId("local"), "at0001"), "active")
        instance.isTerminal = false

        validateVisit(instance)
    }

    @Test
    fun `test DvTemporal visit`() {
        val instance = DvTemporal()

        validateVisit(instance)
    }

    @Test
    fun `test DvText visit`() {
        val instance = DvText()
        instance.value = "test text"

        validateVisit(instance)
    }

    @Test
    fun `test DvTime visit`() {
        val instance = DvTime()
        instance.value = "12:00:00"

        validateVisit(instance)
    }

    @Test
    fun `test DvUri visit`() {
        val instance = DvUri()
        instance.value = "http://test.com"

        validateVisit(instance)
    }

    @Test
    fun `test ReferenceRange visit`() {
        val instance = ReferenceRange()
        instance.meaning = DvText("normal range")
        instance.range = DvInterval(DvQuantity(10.0, "kg"), DvQuantity(20.0, "kg"))

        validateVisit(instance)
    }

    @Test
    fun `test TermMapping visit`() {
        val instance = TermMapping()
        instance.match = "="
        instance.target = CodePhrase(TerminologyId("SNOMED-CT"), "12345")

        validateVisit(instance)
    }
}
