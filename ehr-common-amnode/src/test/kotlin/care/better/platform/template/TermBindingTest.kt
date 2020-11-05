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

package care.better.platform.template

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.am.aom.TermBindingItem
import java.io.IOException

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
class TermBindingTest : AbstractAmTest() {

    @Test
    @Throws(IOException::class)
    fun testTermBindings() {
        val root = AmTreeBuilder(loadTemplate("/DogAPTrace-annot.opt")).build()
        val extTermNode = AmUtils.resolvePath(
            root,
            "/content[openEHR-EHR-OBSERVATION.ap_clamp.v9]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value"
        )
        assertThat(extTermNode).isNotNull
        assertThat(extTermNode!!.rmType).isEqualTo("DV_QUANTITY")
        assertThat(extTermNode.termBindings).isNotNull
        assertThat(extTermNode.termBindings.containsKey("MTH")).isTrue

        val items: List<TermBindingItem> = extTermNode.termBindings.let { it["MTH"] as List<TermBindingItem> }
        assertThat(items).hasSize(2)
        assertThat(items[0].code).isEqualTo("at0004")
        assertThat(items[0].value.codeString).isEqualTo("123456")
        assertThat(items[0].value.terminologyId!!.value).isEqualTo("MTH(2016)")
        assertThat(items[1].code).isEqualTo("at0005")
        assertThat(items[1].value.codeString).isEqualTo("654321")
        assertThat(items[1].value.terminologyId!!.value).isEqualTo("MTH(2016)")
    }

    @Test
    @Throws(IOException::class)
    fun testSecondTermBindings() {
        val root = AmTreeBuilder(loadTemplate("/KorayClinical3.opt")).build()
        val extTermNode = AmUtils.resolvePath(
            root,
            "/content[openEHR-EHR-OBSERVATION.blood_pressure.v1]/data[at0001]/events[at0006]/state[at0007]/items[openEHR-EHR-CLUSTER.level_of_exertion.v1]"
        )

        assertThat(extTermNode).isNotNull
        assertThat(extTermNode!!.termBindings).isNotNull
        assertThat(extTermNode.termBindings.containsKey("SNOMED-CT")).isTrue
        assertThat(extTermNode.termBindings["SNOMED-CT"]).hasSize(4)
    }
}
