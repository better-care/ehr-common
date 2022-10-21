@file:Suppress("UsePropertyAccessSyntax")

package care.better.platform.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition
import org.openehr.rm.datatypes.DvText

class NameUtilsTest {
    @Test
    fun locatableWithNoNameStrict() {
        assertThat(NameUtils.nameMatches(Composition(), listOf("A", "B"), false)).isFalse()
    }

    @Test
    fun locatableWithNoNameRelaxed() {
        assertThat(NameUtils.nameMatches(Composition(), listOf("A", "B"), true)).isFalse()
    }

    @Test
    fun locatableWithNoNameValueStrict() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText() }, listOf("A", "B"), false)).isFalse()
    }

    @Test
    fun locatableWithNoNameValueRelaxed() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText() }, listOf("A", "B"), true)).isFalse()
    }

    @Test
    fun locatableWithWrongNameValueStrict() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText("Bcddef") }, listOf("Abc", "Bcd def"), false)).isFalse()
    }

    @Test
    fun locatableWithWrongNameValueRelaxed() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText("Bcddef") }, listOf("Abc", "Bcd def"), true)).isFalse()
    }

    @Test
    fun locatableWithExactNameValueStrict() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText("Bcd def") }, listOf("Abc", "Bcd def"), false)).isTrue()
    }

    @Test
    fun locatableWithExactNameValueRelaxed() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText("Bcd def") }, listOf("Abc", "Bcd def"), true)).isTrue()
    }

    @Test
    fun locatableWithNumberedNameValueStrict() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText("Bcd def #3") }, listOf("Abc", "Bcd def"), false)).isFalse()
    }

    @Test
    fun locatableWithNumberedNameValueRelaxed() {
        assertThat(NameUtils.nameMatches(Composition().also { it.name = DvText("Bcd def #3") }, listOf("Abc", "Bcd def"), true)).isTrue()
    }
}