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

package care.better.platform.json.jackson

import care.better.platform.json.jackson.openehr.OpenEhrObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.rm.composition.Composition
import org.openehr.rm.composition.Section
import org.openehr.rm.datastructures.Cluster
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
import kotlin.collections.set

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class OpenEhrObjectMapperSerializationTest {

    private val objectMapper: ObjectMapper = OpenEhrObjectMapper().apply {
        this.enable(JsonParser.Feature.ALLOW_COMMENTS)
    }

    @Test
    fun testSimple() {
        val composition: Composition = buildComposition()
        val compositionJson = objectMapper.writeValueAsString(composition)
        val compositionFromJson: Composition = objectMapper.readValue(compositionJson, Composition::class.java)

        assertThat(composition).usingRecursiveComparison().isEqualTo(compositionFromJson)
        val cluster: Cluster = buildCluster()
        val clusterJson = objectMapper.writeValueAsString(cluster)
        val clusterFromJson: Cluster = objectMapper.readValue(clusterJson, Cluster::class.java)
        assertThat(cluster).usingRecursiveComparison().isEqualTo(clusterFromJson)
    }

    @Test
    fun testInArray() {
        val composition: Composition = buildComposition()
        val compositions: MutableList<Composition> = ArrayList<Composition>()
        compositions.add(composition)
        val compositionString = objectMapper.writeValueAsString(compositions)
        val compositionNode = objectMapper.readTree(compositionString)
        assertThat(compositionNode.isArray).isTrue()
        assertThat(compositionNode.path(0).path("_type").textValue()).isEqualTo("COMPOSITION")
        assertThat(compositionNode.path(0).path("content").path(0).path("_type").textValue()).isEqualTo("SECTION")
        assertThat(compositionNode.path(0).path("archetype_node_id").textValue()).isEqualTo("at0000")
    }

    @Test
    fun testInArrayMultipleClasses() {
        val composition: Composition = buildComposition()
        val cluster: Cluster = buildCluster()
        val list: List<Any> = ImmutableList.of<Any>(composition, cluster, "String", 1, 2L)
        val jsonString = objectMapper.writeValueAsString(list)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isArray).isTrue()
        assertThat(node.path(0).path("_type").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path(0).path("content").path(0).path("_type").textValue()).isEqualTo("SECTION")
        assertThat(node.path(0).path("archetype_node_id").textValue()).isEqualTo("at0000")
        assertThat(node.path(1).path("_type").textValue()).isEqualTo("CLUSTER")
        assertThat(node.path(1).path("name").path("_type").textValue()).isEqualTo("DV_CODED_TEXT")
        assertThat(node.path(2).textValue()).isEqualTo("String")
        assertThat(node.path(3).intValue()).isEqualTo(1)
        assertThat(node.path(4).longValue()).isEqualTo(2L)
        val fromJson: List<Any> = objectMapper.readValue(jsonString, object : TypeReference<List<Any>>() {})
        assertThat(fromJson[0]).usingRecursiveComparison().isEqualTo(composition)
        assertThat(fromJson[1]).usingRecursiveComparison().isEqualTo(cluster)
        assertThat(fromJson[2]).isEqualTo("String")
        assertThat(fromJson[3]).isEqualTo(1)
        assertThat(fromJson[4]).isEqualTo(2)
    }

    @Test
    fun testInArrayMultipleClassesWithUntyped() {
        val composition: Composition = buildComposition()
        val cluster: Cluster = buildCluster()
        val map: Map<String, String> = ImmutableMap.of("hello", "world")
        val list: List<Any> = ImmutableList.of(composition, cluster, "String", 1, 2L, map)
        val jsonString = objectMapper.writeValueAsString(list)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isArray).isTrue()
        assertThat(node.path(0).path("_type").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path(0).path("content").path(0).path("_type").textValue()).isEqualTo("SECTION")
        assertThat(node.path(0).path("archetype_node_id").textValue()).isEqualTo("at0000")
        assertThat(node.path(1).path("_type").textValue()).isEqualTo("CLUSTER")
        assertThat(node.path(1).path("name").path("_type").textValue()).isEqualTo("DV_CODED_TEXT")
        assertThat(node.path(2).textValue()).isEqualTo("String")
        assertThat(node.path(3).intValue()).isEqualTo(1)
        assertThat(node.path(4).longValue()).isEqualTo(2L)
        assertThat(node.path(5).path("_type").isMissingNode).isTrue()
        assertThat(node.path(5).path("hello").textValue()).isEqualTo("world")
        val fromJson: List<Any> = objectMapper.readValue(jsonString, object : TypeReference<List<Any>>() {})
        assertThat(fromJson[0]).usingRecursiveComparison().isEqualTo(composition)
        assertThat(fromJson[1]).usingRecursiveComparison().isEqualTo(cluster)
        assertThat(fromJson[2]).isEqualTo("String")
        assertThat(fromJson[3]).isEqualTo(1)
        assertThat(fromJson[4]).isEqualTo(2)
        assertThat(fromJson[5]).usingRecursiveComparison().isEqualTo(map)
    }

    @Test
    fun testSimpleMap() {
        val map: Map<String, Any> = ImmutableMap.of<String, Any>("hello", "world")
        val list: List<Any> = ImmutableList.of<Any>(map)
        val jsonString = objectMapper.writeValueAsString(list)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isArray).isTrue()
        assertThat(node.path(0).path("_type").isMissingNode).isTrue()
        assertThat(node.path(0).path("hello").textValue()).isEqualTo("world")
        val fromJson1: Map<String, Any?> = objectMapper.readValue(objectMapper.writeValueAsString(map), object : TypeReference<Map<String, Any?>>() {})
        assertThat(fromJson1).usingRecursiveComparison().isEqualTo(map)
        val fromJson2: List<Any> = objectMapper.readValue(jsonString, object : TypeReference<List<Any>>() {})
        assertThat(fromJson2[0]).usingRecursiveComparison().isEqualTo(map)
    }

    @Test
    fun testSimpleMapInList() {
        val map1: Map<String, Any> = ImmutableMap.of<String, Any>("hello", "world", "goodbye", "sweet")
        val map2: Map<String, Any> = ImmutableMap.of<String, Any>("one", "two", "three", 1)
        val list: List<Any> = ImmutableList.of<Any>(map1, map2)
        val jsonString = objectMapper.writeValueAsString(list)
        val fromJson = objectMapper.readValue(jsonString, object : TypeReference<List<Any>>() {})
        assertThat(fromJson[0]).usingRecursiveComparison().isEqualTo(map1)
        assertThat(fromJson[1]).usingRecursiveComparison().isEqualTo(map2)
    }

    @Test
    fun testSimpleMapInList1() {
        val fromJson = objectMapper.readValue(
            """[
                                {
                                    "tags": [
                                        {
                                            "tag": "abc1",
                                            "value": "val1",
                                            "aqlPath": "/"
                                        },
                                        {
                                            "tag": "abc2",
                                            "value": "val2",
                                            "aqlPath": "/"
                                        }
                                    ]
                                }
                            ]""",
            object : TypeReference<List<Any>>() {})
        assertThat(fromJson).hasSize(1)
        assertThat(fromJson[0]).isInstanceOf(MutableMap::class.java)
        val map = fromJson[0] as Map<*, *>
        assertThat(map["tags"]).isInstanceOf(MutableList::class.java)
        val tags = map["tags"] as List<*>
        assertThat(tags).containsExactly(
            ImmutableMap.of("tag", "abc1", "value", "val1", "aqlPath", "/"),
            ImmutableMap.of("tag", "abc2", "value", "val2", "aqlPath", "/"))
    }

    @Test
    fun testSimpleRm() {
        val dvText = DvText("hello")
        val list: List<Any> = ImmutableList.of(dvText)
        val jsonString = objectMapper.writeValueAsString(list)
        val fromJson: List<Any> = objectMapper.readValue(jsonString, object : TypeReference<List<Any>>() {})
        assertThat(fromJson[0]).usingRecursiveComparison().isEqualTo(dvText)
    }

    @Test
    fun testInMap() {
        val compositions: MutableMap<String, Composition> = mutableMapOf()
        compositions["data"] = buildComposition()
        val jsonString = objectMapper.writeValueAsString(compositions)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isObject).isTrue()
        assertThat(node.path("data").path("_type").textValue()).isEqualTo("COMPOSITION")
    }

    @Test
    fun testInMapMultipleClasses() {
        val map: MutableMap<String, Any> = HashMap()
        val composition: Composition = buildComposition()
        map["composition"] = composition
        val cluster: Cluster = buildCluster()
        map["cluster"] = cluster
        val jsonString = objectMapper.writeValueAsString(map)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isObject).isTrue()
        assertThat(node.path("composition").path("_type").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path("cluster").path("_type").textValue()).isEqualTo("CLUSTER")
        assertThat(node.path("cluster").path("name").path("_type").textValue()).isEqualTo("DV_CODED_TEXT")
        val fromJson: Map<String, Any?> = objectMapper.readValue(jsonString, object : TypeReference<Map<String, Any?>>() {})
        assertThat(fromJson["composition"]).usingRecursiveComparison().isEqualTo(composition)
        assertThat(fromJson["cluster"]).usingRecursiveComparison().isEqualTo(cluster)
    }

    @Test
    fun testMapInMapMultipleClasses() {
        val map: MutableMap<String, Any> = HashMap()
        val composition: Composition = buildComposition()
        map["composition"] = composition
        val cluster: Cluster = buildCluster()
        map["cluster"] = cluster
        val jsonString = objectMapper.writeValueAsString(ImmutableMap.of<String, Map<String, Any>>("result", map))
        val fromJson: Map<String, Any?> = objectMapper.readValue(jsonString, object : TypeReference<Map<String, Any?>>() {})
        assertThat(fromJson["result"]).isInstanceOf(MutableMap::class.java)
        val subMap = fromJson["result"] as Map<*, *>
        assertThat(subMap["composition"]).usingRecursiveComparison().isEqualTo(composition)
        assertThat(subMap["cluster"]).usingRecursiveComparison().isEqualTo(cluster)
    }

    @Test
    fun testResultSetWithTags() {
        val fromJson: List<*> = objectMapper.readValue(
            OpenEhrObjectMapperSerializationTest::class.java.getResource("/openehr-simple.json"),
            object : TypeReference<List<Any>>() {})
        assertThat(fromJson).isNotNull()
        assertThat(fromJson).hasSize(1)
        assertThat(fromJson[0]).isInstanceOf(MutableMap::class.java)
        assertThat((fromJson[0] as Map<*, *>)["composition"]).isInstanceOf(Composition::class.java)
        val tagsObject2 = (fromJson[0] as Map<*, *>)["tags"]
        assertThat(tagsObject2).isInstanceOf(MutableList::class.java)
        val tags2 = tagsObject2 as List<*>?
        assertThat(tags2!![0]).isInstanceOf(MutableMap::class.java)
    }

    @Test
    fun testWrappedResultSetWithTags() {
        val fromJson: Result =
            objectMapper.readValue(OpenEhrObjectMapperSerializationTest::class.java.getResource("/openehr-result.json"), Result::class.java)
        assertThat(fromJson).isNotNull()
        assertThat(fromJson.resultSet).hasSize(1)
        assertThat(fromJson.resultSet[0]).isInstanceOf(MutableMap::class.java)
        assertThat(fromJson.resultSet[0]["composition"]).isInstanceOf(Composition::class.java)
        val tagsObject = fromJson.resultSet[0]["tags"]
        assertThat(tagsObject).isInstanceOf(MutableList::class.java)
        val tags = tagsObject as List<*>
        assertThat((tags[0] as Map<*, *>)["tag"]).isEqualTo("abc1")
        assertThat((tags[1] as Map<*, *>)["tag"]).isEqualTo("abc2")
    }

    @Test
    fun testWrappedResultSetWithoutTypeWithTags() {
        val fromJson: Result = objectMapper.readValue(
            OpenEhrObjectMapperSerializationTest::class.java.getResource("/openehr-without-type-result.json"),
            Result::class.java)

        assertThat(fromJson).isNotNull()
        assertThat(fromJson.resultSet).hasSize(1)
        assertThat(fromJson.resultSet[0]).isInstanceOf(MutableMap::class.java)
        assertThat(fromJson.resultSet[0]["composition"]).isInstanceOf(Composition::class.java)
        val tagsObject = fromJson.resultSet[0]["tags"]
        assertThat(tagsObject).isInstanceOf(MutableList::class.java)
        val tags = tagsObject as List<*>
        assertThat((tags[0] as Map<*, *>)["tag"]).isEqualTo("abc1")
        assertThat((tags[1] as Map<*, *>)["tag"]).isEqualTo("abc2")
    }

    private fun buildCluster(): Cluster = Cluster().apply { this.name = DvCodedText.createWithLocalTerminology("at0001", "Name") }

    private fun buildComposition(): Composition =
        Composition().apply {
            this.name = DvCodedText.createWithLocalTerminology("111", "Name")
            this.archetypeNodeId = "at0000"
            this.content.add(Section().apply {
                this.name = DvCodedText.createWithLocalTerminology("222", "Name")
            })
        }

    private class Result() {
        var resultSet: MutableList<Map<String, Any?>> = mutableListOf()

        constructor(resultSet: MutableList<Map<String, Any?>>) : this() {
            this.resultSet = resultSet
        }
    }
}
