/* Copyright 2026 Better Ltd (www.better.care)
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

package care.better.platform.terser

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.common.PartySelf
import org.openehr.rm.composition.AdminEntry
import org.openehr.rm.composition.Composition
import org.openehr.rm.composition.Observation
import org.openehr.rm.composition.Section
import org.openehr.rm.datastructures.Element
import org.openehr.rm.datastructures.History
import org.openehr.rm.datastructures.ItemTree
import org.openehr.rm.datastructures.PointEvent
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvQuantity
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 * @since 4.3.1
 */
class RmTerserTest {
    @Test
    fun getValues() {
        val terser = RmTerser(createSection())
        val values = terser.getValues("/items[at0001]/name/value")
        assertThat(values).containsExactly("Entry One", "Entry Two")
    }

    @Test
    fun getValuesWithNamePredicate() {
        val terser = RmTerser(createSection())
        val values = terser.getValues("/items[at0001, 'Entry One']/name/value")
        assertThat(values).containsExactly("Entry One")
    }

    @Test
    fun getValue() {
        val terser = RmTerser(createSection())
        assertThat(terser.getValue("/items[at0001, 'Entry One']/name/value")).isEqualTo("Entry One")
    }

    @Test
    fun getValueReturnsNullForNonExistentPath() {
        val terser = RmTerser(createSection())
        assertThat(terser.getValue("/items[at9999]/name/value")).isNull()
    }

    @Test
    fun getValueThrowsForMultipleValues() {
        val terser = RmTerser(createSection())
        assertThatThrownBy { terser.getValue("/items[at0001]/name/value") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Expected at most one value")
    }

    @Test
    fun getNestedScalarValue() {
        val terser = RmTerser(createSection())
        val values = terser.getValues("/items[at0001, 'Entry One']/data[at0002]/items[at0003]/value/magnitude")
        assertThat(values).containsExactly(37.5)
    }

    @Test
    fun setScalarValue() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.setValue("/items[at0001, 'Entry One']/data[at0002]/items[at0003]/value/magnitude", 38.0)

        val element = (section.items[0] as AdminEntry).data!!.let { it as ItemTree }.items[0] as Element
        val quantity = element.value as DvQuantity
        assertThat(quantity.magnitude).isEqualTo(38.0)
    }

    @Test
    fun setNameValue() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.setValue("/items[at0001, 'Entry One']/name/value", "Updated Name")

        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Updated Name")
    }

    @Test
    fun setNullClearsScalarProperty() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.setValue("/items[at0001, 'Entry One']/data[at0002]/items[at0003]/value", null)

        val element = (section.items[0] as AdminEntry).data!!.let { it as ItemTree }.items[0] as Element
        assertThat(element.value).isNull()
    }

    @Test
    fun setValueReplacesCollectionItem() {
        val section = createSection()
        val terser = RmTerser(section)

        val replacement = AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry One Replacement")
            this.uid = HierObjectId("uid-1-new")
        }

        terser.setValue("/items[at0001, 'Entry One']", replacement)

        assertThat(section.items).hasSize(2)
        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Entry One Replacement")
        assertThat((section.items[1] as AdminEntry).name?.value).isEqualTo("Entry Two")
    }

    @Test
    fun addValueToCollection() {
        val section = createSection()
        val terser = RmTerser(section)

        val newEntry = AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry Three")
            this.uid = HierObjectId("uid-3")
        }

        terser.addValue("/items", newEntry)

        assertThat(section.items).hasSize(3)
        assertThat((section.items[2] as AdminEntry).name?.value).isEqualTo("Entry Three")
    }

    @Test
    fun addValueToNestedCollection() {
        val section = createSection()
        val terser = RmTerser(section)

        val newElement = Element().apply {
            this.archetypeNodeId = "at0004"
            this.name = DvText("Blood Pressure")
            this.value = DvQuantity(120.0, "mmHg")
        }

        terser.addValue("/items[at0001, 'Entry One']/data[at0002]/items", newElement)

        val tree = (section.items[0] as AdminEntry).data!! as ItemTree
        assertThat(tree.items).hasSize(2)
        assertThat(tree.items[1].name?.value).isEqualTo("Blood Pressure")
    }

    @Test
    fun removeValueFromCollectionByNodeId() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001, 'Entry One']")

        assertThat(section.items).hasSize(1)
        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Entry Two")
    }

    @Test
    fun removeValueFromCollectionByUid() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001 and uid/value='uid-2']")

        assertThat(section.items).hasSize(1)
        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Entry One")
    }

    @Test
    fun removeScalarPropertySetsNullAndCleansUpEmptyParent() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001, 'Entry One']/data")

        assertThat(section.items).hasSize(1)
        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Entry Two")
    }

    @Test
    fun removeAllMatchingFromCollection() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001]")

        assertThat(section.items).isEmpty()
    }

    @Test
    fun removeFromNestedCollectionCleansUpEmptyParents() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001, 'Entry One']/data[at0002]/items[at0003]")

        assertThat(section.items).hasSize(1)
        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Entry Two")
    }

    @Test
    fun getValuesFromEmptyCollection() {
        val section = Section().apply {
            this.archetypeNodeId = "at0000"
            this.name = DvText("Empty Section")
        }
        val terser = RmTerser(section)

        assertThat(terser.getValues("/items[at0001]/name/value")).isEmpty()
    }

    @Test
    fun setValueOnMultipleMatchingParents() {
        val section = createSection()
        val terser = RmTerser(section)

        terser.setValue("/items[at0001]/name/value", "Same Name")

        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Same Name")
        assertThat((section.items[1] as AdminEntry).name?.value).isEqualTo("Same Name")
    }

    @Test
    fun compositionGetNestedValue() {
        val terser = RmTerser(createComposition())
        val values = terser.getValues(
            "/content[at1000]/items[at1001, 'Body Temperature']/data[at1002]/events[at1003]/data[at1004]/items[at1005]/value/magnitude"
        )
        assertThat(values).containsExactly(37.2)
    }

    @Test
    fun compositionGetAllObservationNames() {
        val terser = RmTerser(createComposition())
        val values = terser.getValues("/content[at1000]/items[at1001]/name/value")
        assertThat(values).containsExactly("Body Temperature", "Blood Pressure")
    }

    @Test
    fun compositionSetNestedMagnitude() {
        val composition = createComposition()
        val terser = RmTerser(composition)

        terser.setValue(
            "/content[at1000]/items[at1001, 'Body Temperature']/data[at1002]/events[at1003]/data[at1004]/items[at1005]/value/magnitude",
            38.5
        )

        assertThat(terser.getValue(
            "/content[at1000]/items[at1001, 'Body Temperature']/data[at1002]/events[at1003]/data[at1004]/items[at1005]/value/magnitude"
        )).isEqualTo(38.5)
    }

    @Test
    fun compositionRemoveSecondObservation() {
        val composition = createComposition()
        val terser = RmTerser(composition)

        terser.removeValue("/content[at1000]/items[at1001, 'Blood Pressure']")

        val section = composition.content[0] as Section
        assertThat(section.items).hasSize(1)
        assertThat(section.items[0].name?.value).isEqualTo("Body Temperature")
    }

    @Test
    fun compositionRemoveFirstObservation() {
        val composition = createComposition()
        val terser = RmTerser(composition)

        terser.removeValue("/content[at1000]/items[at1001, 'Body Temperature']")

        val section = composition.content[0] as Section
        assertThat(section.items).hasSize(1)
        assertThat(section.items[0].name?.value).isEqualTo("Blood Pressure")
    }

    @Test
    fun compositionAddObservation() {
        val composition = createComposition()
        val terser = RmTerser(composition)

        val newObs = Observation().apply {
            this.archetypeNodeId = "at1001"
            this.name = DvText("Heart Rate")
            this.language = CodePhrase.createLanguagePhrase("en")
            this.encoding = CodePhrase.create("IANA_character-sets", "UTF-8")
            this.subject = PartySelf()
        }

        terser.addValue("/content[at1000]/items", newObs)

        val section = composition.content[0] as Section
        assertThat(section.items).hasSize(3)
        assertThat(section.items[2].name?.value).isEqualTo("Heart Rate")
    }

    @Test
    fun compositionSetComposer() {
        val composition = createComposition()
        val terser = RmTerser(composition)

        val newComposer = PartySelf()
        terser.setValue("/composer", newComposer)

        assertThat(composition.composer).isSameAs(newComposer)
    }

    @Test
    fun compositionRemoveContext() {
        val composition = createComposition().apply {
            this.context = org.openehr.rm.composition.EventContext().apply {
                this.startTime = DvDateTime("2025-01-01T10:00:00Z")
                this.setting = DvCodedText(CodePhrase.create("openehr", "228"), "primary medical care")
            }
        }
        val terser = RmTerser(composition)

        assertThat(composition.context).isNotNull()
        terser.removeValue("/context")
        assertThat(composition.context).isNull()
    }

    @Test
    fun removeSecondElementByName() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.removeValue("/items[at2002, 'Systolic']")

        assertThat(tree.items).hasSize(3)
        assertThat(tree.items.map { it.name?.value }).containsExactly("Heart Rate", "Diastolic", "Systolic #2")
    }

    @Test
    fun removeThirdElementByNodeId() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.removeValue("/items[at2003]")

        assertThat(tree.items).hasSize(3)
        assertThat(tree.items.map { it.name?.value }).containsExactly("Heart Rate", "Systolic", "Systolic #2")
    }

    @Test
    fun removeAllElementsWithSameNodeId() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.removeValue("/items[at2002]")

        assertThat(tree.items).hasSize(2)
        assertThat(tree.items.map { it.name?.value }).containsExactly("Heart Rate", "Diastolic")
    }

    @Test
    fun removeLastElementLeavesOthersIntact() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.removeValue("/items[at2002, 'Systolic #2']")

        assertThat(tree.items).hasSize(3)
        assertThat(tree.items.map { it.name?.value }).containsExactly("Heart Rate", "Systolic", "Diastolic")
    }

    @Test
    fun removeFirstElementLeavesOthersIntact() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.removeValue("/items[at2001]")

        assertThat(tree.items).hasSize(3)
        assertThat(tree.items.map { it.name?.value }).containsExactly("Systolic", "Diastolic", "Systolic #2")
    }

    @Test
    fun setValueOnSecondElementByName() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.setValue("/items[at2002, 'Systolic']/value/magnitude", 140.0)

        assertThat((tree.items[1] as Element).value?.let { (it as DvQuantity).magnitude }).isEqualTo(140.0)
        assertThat((tree.items[3] as Element).value?.let { (it as DvQuantity).magnitude }).isEqualTo(130.0)
    }

    @Test
    fun setValueOnFourthElementByName() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        terser.setValue("/items[at2002, 'Systolic #2']/value/magnitude", 145.0)

        assertThat((tree.items[1] as Element).value?.let { (it as DvQuantity).magnitude }).isEqualTo(120.0)
        assertThat((tree.items[3] as Element).value?.let { (it as DvQuantity).magnitude }).isEqualTo(145.0)
    }

    @Test
    fun getValuesFromMultipleElementsWithSameNodeId() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        val values = terser.getValues("/items[at2002]/value/magnitude")
        assertThat(values).containsExactly(120.0, 130.0)
    }

    @Test
    fun replaceSecondElementInCollection() {
        val tree = createTreeWithMultipleElements()
        val terser = RmTerser(tree)

        val replacement = Element().apply {
            this.archetypeNodeId = "at2002"
            this.name = DvText("Systolic")
            this.value = DvQuantity(150.0, "mmHg")
        }

        terser.setValue("/items[at2002, 'Systolic']", replacement)

        assertThat(tree.items).hasSize(4)
        assertThat((tree.items[1] as Element).value?.let { (it as DvQuantity).magnitude }).isEqualTo(150.0)
        assertThat((tree.items[3] as Element).value?.let { (it as DvQuantity).magnitude }).isEqualTo(130.0)
    }

    @Test
    fun compositionGetMultipleElementMagnitudes() {
        val terser = RmTerser(createComposition())
        val systolic = terser.getValue(
            "/content[at1000]/items[at1001, 'Blood Pressure']/data[at1002]/events[at1003]/data[at1004]/items[at1006]/value/magnitude"
        )
        val diastolic = terser.getValue(
            "/content[at1000]/items[at1001, 'Blood Pressure']/data[at1002]/events[at1003]/data[at1004]/items[at1007]/value/magnitude"
        )
        assertThat(systolic).isEqualTo(120.0)
        assertThat(diastolic).isEqualTo(80.0)
    }

    @Test
    fun compositionRemoveSpecificElementFromObservation() {
        val composition = createComposition()
        val terser = RmTerser(composition)

        terser.removeValue(
            "/content[at1000]/items[at1001, 'Blood Pressure']/data[at1002]/events[at1003]/data[at1004]/items[at1007]"
        )

        val section = composition.content[0] as Section
        val obs = section.items[1] as Observation
        val tree = (obs.data!!.events[0]).data as ItemTree
        assertThat(tree.items).hasSize(1)
        assertThat(tree.items[0].name?.value).isEqualTo("Systolic")
    }

    @Test
    fun getValuesWithExactNameOnlyMatchesExact() {
        val terser = RmTerser(createSectionWithNumberedNodes())
        val values = terser.getValues("/items[at0001, 'Entry']/name/value")
        assertThat(values).containsExactly("Entry")
    }

    @Test
    fun getValuesWithoutNameMatchesAllNodeIds() {
        val terser = RmTerser(createSectionWithNumberedNodes())
        val values = terser.getValues("/items[at0001]/name/value")
        assertThat(values).containsExactly("Entry", "Entry #2", "Entry #3")
    }

    @Test
    fun setValueOnExactNamedEntry() {
        val section = createSectionWithNumberedNodes()
        val terser = RmTerser(section)

        terser.setValue("/items[at0001, 'Entry']/name/value", "Updated")

        assertThat(section.items.map { it.name?.value }).containsExactly("Updated", "Entry #2", "Entry #3")
    }

    @Test
    fun setValueOnNumberedEntry() {
        val section = createSectionWithNumberedNodes()
        val terser = RmTerser(section)

        terser.setValue("/items[at0001, 'Entry #2']/name/value", "Updated")

        assertThat(section.items.map { it.name?.value }).containsExactly("Entry", "Updated", "Entry #3")
    }

    @Test
    fun removeExactNamedEntry() {
        val section = createSectionWithNumberedNodes()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001, 'Entry']")

        assertThat(section.items).hasSize(2)
        assertThat(section.items.map { it.name?.value }).containsExactly("Entry #2", "Entry #3")
    }

    @Test
    fun removeNumberedEntry() {
        val section = createSectionWithNumberedNodes()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001, 'Entry #2']")

        assertThat(section.items).hasSize(2)
        assertThat(section.items.map { it.name?.value }).containsExactly("Entry", "Entry #3")
    }

    @Test
    fun removeAllByNodeIdMatchesAllNumberedNodes() {
        val section = createSectionWithNumberedNodes()
        val terser = RmTerser(section)

        terser.removeValue("/items[at0001]")

        assertThat(section.items).isEmpty()
    }

    @Test
    fun replaceExactNamedEntry() {
        val section = createSectionWithNumberedNodes()
        val terser = RmTerser(section)

        val replacement = AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Replaced")
        }

        terser.setValue("/items[at0001, 'Entry']", replacement)

        assertThat(section.items).hasSize(3)
        assertThat((section.items[0] as AdminEntry).name?.value).isEqualTo("Replaced")
        assertThat((section.items[1] as AdminEntry).name?.value).isEqualTo("Entry #2")
        assertThat((section.items[2] as AdminEntry).name?.value).isEqualTo("Entry #3")
    }

    private fun createSection(): Section = Section().apply {
        this.archetypeNodeId = "at0000"
        this.name = DvText("Test Section")
        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry One")
            this.uid = HierObjectId("uid-1")
            this.data = ItemTree().apply {
                this.archetypeNodeId = "at0002"
                this.name = DvText("Tree")
                this.items.add(Element().apply {
                    this.archetypeNodeId = "at0003"
                    this.name = DvText("Temperature")
                    this.value = DvQuantity(37.5, "°C")
                })
            }
        })
        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry Two")
            this.uid = HierObjectId("uid-2")
        })
    }

    private fun createComposition(): Composition = Composition().apply {
        this.archetypeNodeId = "openEHR-EHR-COMPOSITION.encounter.v1"
        this.name = DvText("Encounter")
        this.language = CodePhrase.createLanguagePhrase("en")
        this.territory = CodePhrase.createTerritoryPhrase("GB")
        this.category = DvCodedText(CodePhrase.create("openehr", "433"), "event")
        this.composer = PartySelf()
        this.content.add(Section().apply {
            this.archetypeNodeId = "at1000"
            this.name = DvText("Vital Signs")
            this.items.add(Observation().apply {
                this.archetypeNodeId = "at1001"
                this.name = DvText("Body Temperature")
                this.language = CodePhrase.createLanguagePhrase("en")
                this.encoding = CodePhrase.create("IANA_character-sets", "UTF-8")
                this.subject = PartySelf()
                this.data = History().apply {
                    this.archetypeNodeId = "at1002"
                    this.name = DvText("History")
                    this.origin = DvDateTime("2025-01-01T10:00:00Z")
                    this.events.add(PointEvent().apply {
                        this.archetypeNodeId = "at1003"
                        this.name = DvText("Any event")
                        this.time = DvDateTime("2025-01-01T10:00:00Z")
                        this.data = ItemTree().apply {
                            this.archetypeNodeId = "at1004"
                            this.name = DvText("Tree")
                            this.items.add(Element().apply {
                                this.archetypeNodeId = "at1005"
                                this.name = DvText("Temperature")
                                this.value = DvQuantity(37.2, "°C")
                            })
                        }
                    })
                }
            })
            this.items.add(Observation().apply {
                this.archetypeNodeId = "at1001"
                this.name = DvText("Blood Pressure")
                this.language = CodePhrase.createLanguagePhrase("en")
                this.encoding = CodePhrase.create("IANA_character-sets", "UTF-8")
                this.subject = PartySelf()
                this.data = History().apply {
                    this.archetypeNodeId = "at1002"
                    this.name = DvText("History")
                    this.origin = DvDateTime("2025-01-01T10:00:00Z")
                    this.events.add(PointEvent().apply {
                        this.archetypeNodeId = "at1003"
                        this.name = DvText("Any event")
                        this.time = DvDateTime("2025-01-01T10:00:00Z")
                        this.data = ItemTree().apply {
                            this.archetypeNodeId = "at1004"
                            this.name = DvText("Tree")
                            this.items.add(Element().apply {
                                this.archetypeNodeId = "at1006"
                                this.name = DvText("Systolic")
                                this.value = DvQuantity(120.0, "mmHg")
                            })
                            this.items.add(Element().apply {
                                this.archetypeNodeId = "at1007"
                                this.name = DvText("Diastolic")
                                this.value = DvQuantity(80.0, "mmHg")
                            })
                        }
                    })
                }
            })
        })
    }

    private fun createTreeWithMultipleElements(): ItemTree = ItemTree().apply {
        this.archetypeNodeId = "at2000"
        this.name = DvText("Tree")
        this.items.add(Element().apply {
            this.archetypeNodeId = "at2001"
            this.name = DvText("Heart Rate")
            this.value = DvQuantity(72.0, "/min")
        })
        this.items.add(Element().apply {
            this.archetypeNodeId = "at2002"
            this.name = DvText("Systolic")
            this.value = DvQuantity(120.0, "mmHg")
        })
        this.items.add(Element().apply {
            this.archetypeNodeId = "at2003"
            this.name = DvText("Diastolic")
            this.value = DvQuantity(80.0, "mmHg")
        })
        this.items.add(Element().apply {
            this.archetypeNodeId = "at2002"
            this.name = DvText("Systolic #2")
            this.value = DvQuantity(130.0, "mmHg")
        })
    }

    private fun createSectionWithNumberedNodes(): Section = Section().apply {
        this.archetypeNodeId = "at0000"
        this.name = DvText("Test Section")
        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry")
            this.uid = HierObjectId("uid-1")
        })
        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry #2")
            this.uid = HierObjectId("uid-2")
        })
        this.items.add(AdminEntry().apply {
            this.archetypeNodeId = "at0001"
            this.name = DvText("Entry #3")
            this.uid = HierObjectId("uid-3")
        })
    }
}
