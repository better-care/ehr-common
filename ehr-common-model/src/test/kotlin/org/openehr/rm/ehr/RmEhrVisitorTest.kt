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

package org.openehr.rm.ehr

import care.better.platform.visitor.RmVisitorTest
import org.junit.jupiter.api.Test
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datatypes.DvText

/**
 * Tests visitor implementation for org.openehr.rm.ehr package classes
 */
class RmEhrVisitorTest : RmVisitorTest() {

    @Test
    fun `test EhrStatus visit`() {
        val instance = EhrStatus()
        instance.name = DvText("EHR Status")
        instance.archetypeNodeId = "openEHR-EHR-EHR_STATUS.generic.v1"
        instance.subject = PartySelf()

        validateVisit(instance)
    }
}
