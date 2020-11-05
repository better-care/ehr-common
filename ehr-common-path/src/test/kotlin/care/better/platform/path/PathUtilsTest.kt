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
import java.util.regex.Pattern

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class PathUtilsTest {
    @Test
    fun testGetSegments() {
        val pathSegments = PathUtils.getPathSegments("/content/items[at0001]/data[at0002]")
        assertThat(pathSegments).hasSize(3)
        assertThat(pathSegments).extracting("element").containsExactly("content", "items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly(null, "at0001", "at0002")
    }

    @Test
    fun testGetSegmentsWithNames() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , 'Name1']/data[at0002 and name/value = 'Name2']")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("Name1", "Name2")
    }

    @Test
    fun testFirstGetSegmentsWithNames() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , 'ČŠŽ']/data[at0002 AND NAME/VALUE = 'Name2']")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠŽ", "Name2")
    }

    @Test
    fun testSecondGetSegmentsWithNames() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , \"ČŠŽ\"]/data[at0002 AND NAME/VALUE = \"Name2\"]")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠŽ", "Name2")
    }

    @Test
    fun testThirdGetSegmentsWithNames() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , \"ČŠ\\\"Ž\"]/data[at0002 AND NAME/VALUE = \"Na\\\"me2\"]")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠ\"Ž", "Na\"me2")
    }

    @Test
    fun testForthGetSegmentsWithNames() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , 'ČŠ\"Ž']/data[at0002 AND NAME/VALUE = 'Na\"me2']")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠ\"Ž", "Na\"me2")
    }

    @Test
    fun testFifthGetSegmentsWithNames() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , 'ČŠ\\'Ž']/data[at0002 AND NAME/VALUE = 'Na\\'me2']")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠ'Ž", "Na'me2")
        assertThat(pathSegments).extracting("prefix").containsExactly(null, null)
    }

    @Test
    fun testGetSegmentsWithNamesAndUids() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 , 'ČŠ\\'Ž']/data[at0002 AND uid/value = 'uid']")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠ'Ž", "uid")
        assertThat(pathSegments).extracting("prefix").containsExactly(null, "uid/value")
    }

    @Test
    fun testSecondGetSegmentsWithNamesAndUids() {
        val pathSegments = PathUtils.getPathSegments("/items[at0001 and name/value='ČŠ\\'Ž']/data[at0002 AND uid/value='uid']")
        assertThat(pathSegments).hasSize(2)
        assertThat(pathSegments).extracting("element").containsExactly("items", "data")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("at0001", "at0002")
        assertThat(pathSegments).extracting("name").containsExactly("ČŠ'Ž", "uid")
        assertThat(pathSegments).extracting("prefix").containsExactly(null, "uid/value")

        val joinedPath: String = pathSegments.joinToString("/") { it.asPathSegment() }
        assertThat(joinedPath).isEqualTo("items[at0001,'ČŠ\\'Ž']/data[at0002 and uid/value='uid']")
    }

    @Test
    fun testInexactArchetype() {
        val pathSegments = PathUtils.getPathSegments("/content[openEHR-EHR-ADMIN_ENTRY.metadata*]/data[at0001]/items[at0003]/value")
        assertThat(pathSegments).hasSize(4)
        assertThat(pathSegments).extracting("element").containsExactly("content", "data", "items", "value")
        assertThat(pathSegments).extracting("archetypeNodeId").containsExactly("openEHR-EHR-ADMIN_ENTRY.metadata*", "at0001", "at0003", null)
    }

    @Test
    fun testRegex() {
        val pattern = Pattern.compile("('(.*(?<!\\\\))')|(\"(.*(?<!\\\\))\")")

        val firstMatcher = pattern.matcher("'Test'")
        assertThat(firstMatcher.matches()).isTrue
        assertThat(firstMatcher.groupCount()).isEqualTo(4)
        assertThat(firstMatcher.group(2)).isEqualTo("Test")

        val secondMatcher = pattern.matcher("\"Test\"")
        assertThat(secondMatcher.matches()).isTrue
        assertThat(secondMatcher.groupCount()).isEqualTo(4)
        assertThat(secondMatcher.group(4)).isEqualTo("Test")
    }

    @Test
    fun testSecondRegex1() {
        val pattern = Pattern.compile("('(.*(?<!\\\\))')|(\"(.*(?<!\\\\))\")")

        val firstMatcher = pattern.matcher("'Te\\\\'st'")
        assertThat(firstMatcher.matches()).isTrue
        assertThat(firstMatcher.groupCount()).isEqualTo(4)
        assertThat(firstMatcher.group(2)).isEqualTo("Te\\\\'st")

        val secondMatcher = pattern.matcher("\"Te'st\"")
        assertThat(secondMatcher.matches()).isTrue
        assertThat(secondMatcher.groupCount()).isEqualTo(4)
        assertThat(secondMatcher.group(4)).isEqualTo("Te'st")

        val thirdMatcher = pattern.matcher("\"Te\\\\\"st\"")
        assertThat(thirdMatcher.matches()).isTrue
        assertThat(thirdMatcher.groupCount()).isEqualTo(4)
        assertThat(thirdMatcher.group(4)).isEqualTo("Te\\\\\"st")
    }

    @Test
    fun testPropertyName() {
        assertThat(PathUtils.getPropertyName("archetype_node_id")).isEqualTo("archetypeNodeId")
    }

    @Test
    fun testUnderscorePath() {
        assertThat(PathUtils.underscorePath("helloWorld")).isEqualTo("hello_world")
        assertThat(PathUtils.underscorePath("hello_a_World")).isEqualTo("hello_a_world")
    }

    @Test
    fun testDoubleArchetype() {
        val pathSegments =
            PathUtils.getPathSegments("[openEHR-EHR-COMPOSITION.encounter.v1]/content[openEHR-EHR-OBSERVATION.glasgow_coma_scale.v1]/protocol[at0038]/items[at0039][openEHR-EHR-CLUSTER.exam_eye_pupil.v0]/items[at0002]")
        assertThat(pathSegments[2].archetypeNodeId).isEqualTo("openEHR-EHR-CLUSTER.exam_eye_pupil.v0")
    }
}
