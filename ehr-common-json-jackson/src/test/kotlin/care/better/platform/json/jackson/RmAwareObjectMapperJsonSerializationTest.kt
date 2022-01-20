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

import care.better.platform.json.jackson.better.BetterObjectMapper
import care.better.platform.json.jackson.openehr.OpenEhrObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.composition.Composition
import org.openehr.rm.composition.Section
import org.openehr.rm.datastructures.Cluster
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvCodedText.Companion.create
import org.openehr.rm.datatypes.DvText
import java.io.IOException
import java.time.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.set

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class RmAwareObjectMapperJsonSerializationTest {
    private val objectMapper: ObjectMapper = BetterObjectMapper().apply { this.enable(JsonParser.Feature.ALLOW_COMMENTS) }

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
    fun teseInArray() {
        val composition: Composition = buildComposition()
        val compositions: MutableList<Composition> = ArrayList()
        compositions.add(composition)
        val jsonString = objectMapper.writeValueAsString(compositions)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isArray).isTrue
        assertThat(node.path(0).path("@class").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path(0).path("content").path(0).path("@class").textValue()).isEqualTo("SECTION")
        assertThat(node.path(0).path("archetype_node_id").textValue()).isEqualTo("at0000")
    }

    @Test
    fun testInArrayMultipleClasses() {
        val composition: Composition = buildComposition()
        val cluster: Cluster = buildCluster()
        val list: List<Any> = ImmutableList.of<Any>(composition, cluster, "String", 1, 2L)
        val jsonString = objectMapper.writeValueAsString(list)
        val node = objectMapper.readTree(jsonString)
        assertThat(node.isArray).isTrue
        assertThat(node.path(0).path("@class").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path(0).path("content").path(0).path("@class").textValue()).isEqualTo("SECTION")
        assertThat(node.path(0).path("archetype_node_id").textValue()).isEqualTo("at0000")
        assertThat(node.path(1).path("@class").textValue()).isEqualTo("CLUSTER")
        assertThat(node.path(1).path("name").path("@class").textValue()).isEqualTo("DV_CODED_TEXT")
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
        assertThat(node.isArray).isTrue
        assertThat(node.path(0).path("@class").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path(0).path("content").path(0).path("@class").textValue()).isEqualTo("SECTION")
        assertThat(node.path(0).path("archetype_node_id").textValue()).isEqualTo("at0000")
        assertThat(node.path(1).path("@class").textValue()).isEqualTo("CLUSTER")
        assertThat(node.path(1).path("name").path("@class").textValue()).isEqualTo("DV_CODED_TEXT")
        assertThat(node.path(2).textValue()).isEqualTo("String")
        assertThat(node.path(3).intValue()).isEqualTo(1)
        assertThat(node.path(4).longValue()).isEqualTo(2L)
        assertThat(node.path(5).path("@class").isMissingNode).isTrue
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
        assertThat(node.isArray).isTrue
        assertThat(node.path(0).path("@class").isMissingNode).isTrue
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
        assertThat(node.isObject).isTrue
        assertThat(node.path("data").path("@class").textValue()).isEqualTo("COMPOSITION")
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
        assertThat(node.isObject).isTrue
        assertThat(node.path("composition").path("@class").textValue()).isEqualTo("COMPOSITION")
        assertThat(node.path("cluster").path("@class").textValue()).isEqualTo("CLUSTER")
        assertThat(node.path("cluster").path("name").path("@class").textValue()).isEqualTo("DV_CODED_TEXT")
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
            RmAwareObjectMapperJsonSerializationTest::class.java.getResource("/simple.json"),
            MutableList::class.java)

        assertThat(fromJson).isNotNull
        assertThat(fromJson).hasSize(1)
        assertThat(fromJson[0]).isInstanceOf(MutableMap::class.java)
        assertThat((fromJson[0] as Map<*, *>)["composition"]).isInstanceOf(Composition::class.java)
        val tagsObject2 = (fromJson[0] as Map<*, *>)["tags"]
        assertThat(tagsObject2).isInstanceOf(MutableList::class.java)
        val tags2 = tagsObject2 as List<*>
        assertThat(tags2[0]).isInstanceOf(MutableMap::class.java)
    }

    @Test
    fun testWrappedResultSetWithTags() {
        val fromJson = objectMapper.readValue(RmAwareObjectMapperJsonSerializationTest::class.java.getResource("/result.json"), Result::class.java)
        assertThat(fromJson).isNotNull
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
    @Throws(IOException::class)
    fun rmAwareTest() {
        val map: MutableMap<String, Any> = HashMap()
        map["simple"] = "abc"
        map["simple1"] = 1
        map["simple2"] = 1.7
        map["simple3"] = OffsetDateTime.now()
        map["rm1"] = create("local", "code", "label")
        val list: MutableList<Any> = LinkedList()
        list.add("abc")
        list.add(1)
        list.add(1.7)
        list.add(OffsetDateTime.now())
        list.add(create("local", "code", "label"))
        val set: MutableSet<Any> = HashSet()
        set.add("abc")
        set.add(1)
        set.add(1.7)
        set.add(OffsetDateTime.now())
        set.add(create("local", "code", "label"))
        val mapper = BetterObjectMapper()
        val mapJson = mapper.writeValueAsString(map)
        val listJson = mapper.writeValueAsString(list)
        val setJson = mapper.writeValueAsString(set)
        assertThat(mapJson)
            .contains("\"@class\":\"DV_CODED_TEXT\"")
            .contains("\"@class\":\"CODE_PHRASE\"")
            .contains("\"@class\":\"TERMINOLOGY_ID\"")
        assertThat(listJson)
            .contains("\"@class\":\"DV_CODED_TEXT\"")
            .contains("\"@class\":\"CODE_PHRASE\"")
            .contains("\"@class\":\"TERMINOLOGY_ID\"")
        assertThat(setJson)
            .contains("\"@class\":\"DV_CODED_TEXT\"")
            .contains("\"@class\":\"CODE_PHRASE\"")
            .contains("\"@class\":\"TERMINOLOGY_ID\"")
        assertThat(StringUtils.countMatches(mapJson, "@class")).isEqualTo(3)
        assertThat(StringUtils.countMatches(listJson, "@class")).isEqualTo(3)
        assertThat(StringUtils.countMatches(setJson, "@class")).isEqualTo(3)
        val openEhrMapper = OpenEhrObjectMapper()
        val openEhrMapJson = openEhrMapper.writeValueAsString(map)
        val openEhrListJson = openEhrMapper.writeValueAsString(list)
        val openEhrSetJson = openEhrMapper.writeValueAsString(set)
        assertThat(openEhrMapJson)
            .contains("\"_type\":\"DV_CODED_TEXT\"")
            .contains("\"_type\":\"CODE_PHRASE\"")
            .contains("\"_type\":\"TERMINOLOGY_ID\"")
        assertThat(openEhrListJson)
            .contains("\"_type\":\"DV_CODED_TEXT\"")
            .contains("\"_type\":\"CODE_PHRASE\"")
            .contains("\"_type\":\"TERMINOLOGY_ID\"")
        assertThat(openEhrSetJson)
            .contains("\"_type\":\"DV_CODED_TEXT\"")
            .contains("\"_type\":\"CODE_PHRASE\"")
            .contains("\"_type\":\"TERMINOLOGY_ID\"")
        assertThat(StringUtils.countMatches(openEhrMapJson, "_type")).isEqualTo(3)
        assertThat(StringUtils.countMatches(openEhrListJson, "_type")).isEqualTo(3)
        assertThat(StringUtils.countMatches(openEhrSetJson, "_type")).isEqualTo(3)
        val reconstructedMap: Map<*, *> = openEhrMapper.readValue(openEhrMapJson, MutableMap::class.java)
        val rm1 = reconstructedMap["rm1"]
        assertThat(rm1).isOfAnyClassIn(DvCodedText::class.java)
        assertThat((rm1 as DvCodedText?)!!.definingCode).isOfAnyClassIn(CodePhrase::class.java)
        assertThat(rm1!!.definingCode!!.terminologyId).isOfAnyClassIn(TerminologyId::class.java)
        val reconstructedList: List<*> = openEhrMapper.readValue(openEhrListJson, MutableList::class.java)
        val rm2 = reconstructedList[reconstructedList.size - 1]!!
        assertThat(rm2).isOfAnyClassIn(DvCodedText::class.java)
        assertThat((rm2 as DvCodedText).definingCode).isOfAnyClassIn(CodePhrase::class.java)
        assertThat(rm2.definingCode!!.terminologyId).isOfAnyClassIn(TerminologyId::class.java)
        val reconstructedSet: Set<*> = openEhrMapper.readValue(openEhrSetJson, MutableSet::class.java)
        val rm3 = reconstructedSet.stream()
            .filter { e: Any? -> e is DvCodedText }
            .findAny()
            .orElse(null)!!
        assertThat(rm3).isNotNull
        assertThat(rm3).isInstanceOf(DvCodedText::class.java)
        assertThat((rm3 as DvCodedText).definingCode).isInstanceOf(CodePhrase::class.java)
        assertThat(rm3.definingCode!!.terminologyId).isInstanceOf(TerminologyId::class.java)
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
