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

package care.better.platform.path

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.composition.AdminEntry
import org.openehr.rm.composition.Section
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class NameAndNodeMatchingPathValueExtractorTest {
    private var section: Section = Section().apply {
        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Name")
            this.uid = HierObjectId("12345")
        })

        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Name #2")
            this.uid = HierObjectId("123456")
        })
    }

    @Test
    fun testWithName() {
        val firstExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001, 'Name']/name/value")
        assertThat(firstExtractor.getValue(section)).containsExactly("Name")

        val secondExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001, 'Name1']/name/value")
        assertThat(secondExtractor.getValue(section)).isEmpty()

        val thirdExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001]/name/value")
        assertThat(thirdExtractor.getValue(section)).containsExactly("Name", "Name #2")

        val forthExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 and name/value='Name']/name/value")
        assertThat(forthExtractor.getValue(section)).containsExactly("Name")

        val fifthExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 and name/value='Name1']/name/value")
        assertThat(fifthExtractor.getValue(section)).isEmpty()

        val sixthExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 and uid/value='Name']/name/value")
        assertThat(sixthExtractor.getValue(section)).isEmpty()

        val seventhExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 AND NAME/VALUE='Name']/name/value")
        assertThat(seventhExtractor.getValue(section)).containsExactly("Name")

        val eightExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 AND NAME/VALUE='Name1']/name/value")
        assertThat(eightExtractor.getValue(section)).isEmpty()

        val ninthExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001, 'Name']/name/value", true)
        assertThat(ninthExtractor.getValue(section)).containsExactly("Name", "Name #2")

        val tenthExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001, 'Name1']/name/value", true)
        assertThat(tenthExtractor.getValue(section)).isEmpty()

    }

    @Test
    fun testWithUid() {
        val firstExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 and uid/value='12345']/name/value")
        assertThat(firstExtractor.getValue(section)).containsExactly("Name")
        val secondExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 and uid/value='xx']/name/value")
        assertThat(secondExtractor.getValue(section)).isEmpty()
        val thirdExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 AND UID/VALUE='12345']/name/value")
        assertThat(thirdExtractor.getValue(section)).containsExactly("Name")
        val forthExtractor = NameAndNodeMatchingPathValueExtractor("/items[at0001 AND UID/VALUE='xx']/name/value")
        assertThat(forthExtractor.getValue(section)).isEmpty()
    }
}
