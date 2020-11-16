package care.better.platform.utils

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition

/**
 * @author Primoz Delopst
 */
class RmUtilsTest {

    @Test
    fun getRmClassTest() {
        assertThat(RmUtils.getRmClass("Composition")).isEqualTo(Composition::class.java)
    }

    @Test
    fun getRmClassTestFailed() {
        assertThatThrownBy { RmUtils.getRmClass("String") }.isInstanceOf(ClassNotFoundException::class.java)
    }

    @Test
    fun testRequiredFields() {
        assertThat(RmUtils.getRequiredFields("Composition")).extracting("name").contains("language")
    }

    @Test
    fun fieldForAttributeTest() {
        assertThat(RmUtils.getFieldForAttribute("test_attribute")).isEqualTo("testAttribute")
    }

    @Test
    fun attributeForFieldTest() {
        assertThat(RmUtils.getAttributeForField("testAttribute")).isEqualTo("test_attribute")
    }

    @Test
    fun rmTypeNameTest() {
        assertThat(RmUtils.getRmTypeName(Composition::class.java)).isEqualTo("COMPOSITION")
    }
}