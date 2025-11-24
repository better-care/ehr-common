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
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvDuration
import org.openehr.rm.datatypes.DvText

/**
 * Tests visitor implementation for org.openehr.rm.datastructures package classes
 */
class RmDatastructuresVisitorTest : RmVisitorTest() {

    @Test
    fun `test Cluster visit`() {
        val instance = Cluster()
        instance.name = DvText("Cluster name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test Element visit`() {
        val instance = Element()
        instance.name = DvText("Element name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test History visit`() {
        val instance = History()
        instance.name = DvText("History name")
        instance.archetypeNodeId = "at0001"
        instance.origin = DvDateTime("2024-01-01T00:00:00Z")

        validateVisit(instance)
    }

    @Test
    fun `test IntervalEvent visit`() {
        val instance = IntervalEvent()
        instance.name = DvText("IntervalEvent name")
        instance.archetypeNodeId = "at0001"
        instance.time = DvDateTime("2024-01-01T00:00:00Z")
        instance.data = ItemTree()
        instance.data?.archetypeNodeId = "at0002"
        instance.data?.name = DvText("Data")
        instance.width = DvDuration("PT1H")

        validateVisit(instance)
    }

    @Test
    fun `test ItemList visit`() {
        val instance = ItemList()
        instance.name = DvText("ItemList name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test ItemSingle visit`() {
        val instance = ItemSingle()
        instance.name = DvText("ItemSingle name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test ItemTable visit`() {
        val instance = ItemTable()
        instance.name = DvText("ItemTable name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test ItemTree visit`() {
        val instance = ItemTree()
        instance.name = DvText("ItemTree name")
        instance.archetypeNodeId = "at0001"

        validateVisit(instance)
    }

    @Test
    fun `test PointEvent visit`() {
        val instance = PointEvent()
        instance.name = DvText("PointEvent name")
        instance.archetypeNodeId = "at0001"
        instance.time = DvDateTime("2024-01-01T00:00:00Z")
        instance.data = ItemTree()
        instance.data?.archetypeNodeId = "at0002"
        instance.data?.name = DvText("Data")

        validateVisit(instance)
    }
}
