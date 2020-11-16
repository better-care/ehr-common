package care.better.platform.json.jackson

import care.better.platform.json.jackson.openehr.OpenEhrObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition
import java.io.IOException

/**
 * @author Primoz Delopst
 */
class OpenEhrObjectMapperTest {
    @Test
    fun testConvertToCompositionSimpleWithType() {
        val openEhrObjectMapper = OpenEhrObjectMapper()
        val composition: Composition = openEhrObjectMapper.readValue(
                OpenEhrObjectMapperTest::class.java.getResource("/simple-json.json"),
                Composition::class.java)
        assertThat(composition.name!!.value).isEqualTo("Report")
    }

    @Test
    @Throws(IOException::class)
    fun testConvertToCompositionSimpleWithoutType() {
        val openEhrObjectMapper = OpenEhrObjectMapper()
        val composition: Composition = openEhrObjectMapper.readValue(
                OpenEhrObjectMapperTest::class.java.getResource("/simple-json-without-type.json"),
                Composition::class.java)
        assertThat(composition.name?.value).isEqualTo("Report")
    }

    @Test
    @Throws(IOException::class)
    fun testConvertToCompositionWithType() {
        val openEhrObjectMapper = OpenEhrObjectMapper()
        val composition: Composition = openEhrObjectMapper.readValue(
                OpenEhrObjectMapperTest::class.java.getResource("/complex-json.json"),
                Composition::class.java)
        assertThat(composition.name?.value).isEqualTo("Report")
        assertThat(composition.context?.otherContext?.name?.value).isEqualTo("IZahl")
    }

    @Test
    @Throws(IOException::class)
    fun testConvertToCompositionWithoutType() {
        val openEhrObjectMapper = OpenEhrObjectMapper()
        val composition: Composition = openEhrObjectMapper.readValue(
                OpenEhrObjectMapperTest::class.java.getResource("/complex-json-without-type.json"),
                Composition::class.java)
        assertThat(composition.name?.value).isEqualTo("Report")
        assertThat(composition.context?.otherContext?.name?.value).isEqualTo("IZahl")
    }
}