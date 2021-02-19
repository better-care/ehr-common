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

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class PathSegmentTest {
    @Test
    fun testMatchSegment() {
        val ps1 = PathSegment("composition", "openEHR-EHR-COMPOSITION.request.v1")
        val ps2 = PathSegment("content", "openEHR-EHR-INSTRUCTION.request.v1", "Request for Service")
        val ps3 = PathSegment("activities", "at0001", "Request")
        val ps7 = PathSegment("activities", "at0001", "909876", "uid/value")
        val ps4 = PathSegment("composition", "openEHR-EHR-COMPOSITION.request.v1")
        val ps5 = PathSegment("content", "openEHR-EHR-INSTRUCTION.request.v1", "Request for Service")
        val ps6 = PathSegment("activities", "at0001", "Request")
        val ps8 = PathSegment("activities", "at0001", "909876", "uid/value")

        assertMatch(ps1, ps4)
        assertMatch(ps2, ps5)
        assertMatch(ps3, ps6)
        assertMatch(ps7, ps8)

        val ps9 = PathSegment("composition", null)
        assertMatch(ps1, ps9)

        val ps10 = PathSegment("composition", "openEHR-EHR-COMPOSITION.encounter.v1")
        assertMismatch(ps1, ps10)

        val ps11 = PathSegment("whatever", "openEHR-EHR-COMPOSITION.request.v1")
        assertMismatch(ps1, ps11)

        val ps12 = PathSegment("content", "openEHR-EHR-INSTRUCTION.request.v1")
        assertMatch(ps2, ps12)

        val ps13 = PathSegment("content", "openEHR-EHR-INSTRUCTION.request.v1", "Request for Whatever")
        assertMismatch(ps2, ps13)

        val ps14 = PathSegment("content", "openEHR-EHR-INSTRUCTION.request.v2", "Request for Service")
        assertMismatch(ps2, ps14)
        assertMismatch(ps2, ps13)

        val ps16 = PathSegment("activities", "at0001")
        assertMatch(ps3, ps16)

        val ps17 = PathSegment("activities", null)
        assertMatch(ps3, ps17)

        val ps17b = PathSegment("activities", "at0002")
        assertMismatch(ps3, ps17b)

        val ps18 = PathSegment("activities", "at0001", "909876", "uid/value2")
        assertMismatch(ps7, ps18)

        val ps19 = PathSegment("activities", "at0001", "909875", "uid/value")
        assertMismatch(ps7, ps19)

        val ps20 = PathSegment("activities", null)
        assertMatch(ps7, ps20)

        val ps21 = PathSegment("activities", "at0001")
        assertMatch(ps7, ps21)
    }

    private fun assertMatch(firstPathSegment: PathSegment, secondPathSegment: PathSegment) {
        assertThat(firstPathSegment.matchesSegment(secondPathSegment)).isTrue
        assertThat(secondPathSegment.matchesSegment(firstPathSegment)).isTrue
    }

    private fun assertMismatch(firstPathSegment: PathSegment, secondPathSegment: PathSegment) {
        assertThat(firstPathSegment.matchesSegment(secondPathSegment)).isFalse
        assertThat(secondPathSegment.matchesSegment(firstPathSegment)).isFalse
    }
}
