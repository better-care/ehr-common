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

package org.openehr.rm.datastructures

import care.better.platform.visitor.RmVisitorTest
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.common.Archetyped
import org.openehr.rm.common.FeederAudit
import org.openehr.rm.common.Link
import org.openehr.rm.datatypes.*

/**
 * Tests visitor implementation for org.openehr.rm.datastructures package classes
 */
class RmDatastructuresVisitorTest : RmVisitorTest() {

    @Test
    fun `test Cluster visit`() {
        val instance = Cluster()
        // Locatable properties
        instance.name = DvText("Cluster name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-cluster-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-CLUSTER.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // Cluster properties
        instance.items.add(Element().apply {
            name = DvText("Element in cluster")
            archetypeNodeId = "at0002"
            value = DvText("Element value")
        })

        validateVisit(instance)
    }

    @Test
    fun `test Element visit`() {
        val instance = Element()
        // Locatable properties
        instance.name = DvText("Element name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-element-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ELEMENT.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // Element properties
        instance.value = DvText("Element value")
        instance.nullFlavour = DvCodedText(CodePhrase(TerminologyId("openehr"), "271"), "no information")
        instance.nullReason = DvText("Test null reason")

        validateVisit(instance)
    }

    @Test
    fun `test History visit`() {
        val instance = History()
        // Locatable properties
        instance.name = DvText("History name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-history-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-HISTORY.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // History properties
        instance.origin = DvDateTime("2024-01-01T00:00:00Z")
        instance.period = DvDuration("PT1H")
        instance.duration = DvDuration("P1D")
        instance.summary = ItemTree().apply {
            name = DvText("Summary")
            archetypeNodeId = "at0002"
        }
        instance.events.add(PointEvent().apply {
            name = DvText("Event")
            archetypeNodeId = "at0003"
            time = DvDateTime("2024-01-01T01:00:00Z")
            data = ItemTree().apply {
                name = DvText("Event data")
                archetypeNodeId = "at0004"
            }
        })

        validateVisit(instance)
    }

    @Test
    fun `test IntervalEvent visit`() {
        val instance = IntervalEvent()
        // Locatable properties
        instance.name = DvText("IntervalEvent name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-intervalevent-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-INTERVAL_EVENT.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // Event properties
        instance.time = DvDateTime("2024-01-01T00:00:00Z")
        instance.data = ItemTree().apply {
            archetypeNodeId = "at0002"
            name = DvText("Data")
        }
        instance.state = ItemTree().apply {
            archetypeNodeId = "at0003"
            name = DvText("State")
        }
        // IntervalEvent properties
        instance.width = DvDuration("PT1H")
        instance.mathFunction = DvCodedText(CodePhrase(TerminologyId("openehr"), "1"), "mean")
        instance.sampleCount = 10

        validateVisit(instance)
    }

    @Test
    fun `test ItemList visit`() {
        val instance = ItemList()
        // Locatable properties
        instance.name = DvText("ItemList name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-itemlist-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ITEM_LIST.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // ItemList properties
        instance.items.add(Element().apply {
            name = DvText("Element in list")
            archetypeNodeId = "at0002"
            value = DvText("Element value")
        })

        validateVisit(instance)
    }

    @Test
    fun `test ItemSingle visit`() {
        val instance = ItemSingle()
        // Locatable properties
        instance.name = DvText("ItemSingle name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-itemsingle-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ITEM_SINGLE.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // ItemSingle properties
        instance.item = Element().apply {
            name = DvText("Single element")
            archetypeNodeId = "at0002"
            value = DvText("Element value")
        }

        validateVisit(instance)
    }

    @Test
    fun `test ItemTable visit`() {
        val instance = ItemTable()
        // Locatable properties
        instance.name = DvText("ItemTable name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-itemtable-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ITEM_TABLE.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // ItemTable properties
        instance.rows.add(Cluster().apply {
            name = DvText("Table row")
            archetypeNodeId = "at0002"
        })

        validateVisit(instance)
    }

    @Test
    fun `test ItemTree visit`() {
        val instance = ItemTree()
        // Locatable properties
        instance.name = DvText("ItemTree name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-itemtree-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-ITEM_TREE.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // ItemTree properties
        instance.items.add(Element().apply {
            name = DvText("Element in tree")
            archetypeNodeId = "at0002"
            value = DvText("Element value")
        })

        validateVisit(instance)
    }

    @Test
    fun `test PointEvent visit`() {
        val instance = PointEvent()
        // Locatable properties
        instance.name = DvText("PointEvent name")
        instance.archetypeNodeId = "at0001"
        instance.uid = HierObjectId("test-pointevent-uid")
        instance.links.add(Link().apply {
            meaning = DvText("link meaning")
            type = DvText("link type")
            target = DvEhrUri("ehr://test")
        })
        instance.archetypeDetails = Archetyped().apply {
            archetypeId = ArchetypeId("openEHR-EHR-POINT_EVENT.test.v1")
            rmVersion = "1.0.4"
        }
        instance.feederAudit = FeederAudit()
        // Event properties
        instance.time = DvDateTime("2024-01-01T00:00:00Z")
        instance.data = ItemTree().apply {
            archetypeNodeId = "at0002"
            name = DvText("Data")
        }
        instance.state = ItemTree().apply {
            archetypeNodeId = "at0003"
            name = DvText("State")
        }

        validateVisit(instance)
    }
}
