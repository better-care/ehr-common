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

import care.better.platform.utils.exception.RmClassCastException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class RmUtilsTest {

    @Test
    fun testGetRmClassTest() {
        assertThat(RmUtils.getRmClass("Composition")).isEqualTo(Composition::class.java)
    }

    @Test
    fun testGetRmClassTestFailed() {
        assertThatThrownBy { RmUtils.getRmClass("String") }.isInstanceOf(RmClassCastException::class.java)
    }

    @Test
    fun testRequiredFields() {
        assertThat(RmUtils.getRequiredFields("Composition")).extracting("name").contains("language")
    }

    @Test
    fun testFieldForAttributeTest() {
        assertThat(RmUtils.getFieldForAttribute("test_attribute")).isEqualTo("testAttribute")
    }

    @Test
    fun testAttributeForFieldTest() {
        assertThat(RmUtils.getAttributeForField("testAttribute")).isEqualTo("test_attribute")
    }

    @Test
    fun testRmTypeNameTest() {
        assertThat(RmUtils.getRmTypeName(Composition::class.java)).isEqualTo("COMPOSITION")
    }

    @Test
    fun testGetAllFieldsForCodePhrase() {
        val fields = RmUtils.getAllFields("CODE_PHRASE")
        assertThat(fields).hasSize(3)
    }
}
