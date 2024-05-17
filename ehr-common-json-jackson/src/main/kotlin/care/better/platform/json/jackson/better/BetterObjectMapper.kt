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

package care.better.platform.json.jackson.better

import care.better.platform.annotation.Open
import care.better.platform.json.jackson.mixedin.BooleanContextExpressionMixedIn
import care.better.platform.json.jackson.rm.RmTypeResolverBuilder
import care.better.platform.json.jackson.time.OpenEhrTimeModule
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.openehr.proc.taskplanning.BooleanContextExpression

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@Open
class BetterObjectMapper : ObjectMapper {
    constructor() : super()
    constructor(src: BetterObjectMapper) : super(src)
    constructor(src: BetterObjectMapper, factory: JsonFactory) : super(src, factory)

    init {
        this.defaultInit()
    }

    override fun copy(): ObjectMapper {
        _checkInvalidCopy(BetterObjectMapper::class.java)
        return BetterObjectMapper(this).also { it.defaultInit() }
    }

    override fun copyWith(factory: JsonFactory): ObjectMapper {
        _checkInvalidCopy(BetterObjectMapper::class.java)
        return BetterObjectMapper(this, factory).also { it.defaultInit() }
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }
}

fun ObjectMapper.defaultInit() {
    this.setDefaultTyping(RmTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL).init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY))

    this.registerKotlinModule()
    this.registerModule(JavaTimeModule())
    this.registerModule(OpenEhrTimeModule())

    this.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
    this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    this.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
    this.addMixIn(BooleanContextExpression::class.java, BooleanContextExpressionMixedIn::class.java)
    this.propertyNamingStrategy = BetterPlatformPropertyNamingStrategy()
}
