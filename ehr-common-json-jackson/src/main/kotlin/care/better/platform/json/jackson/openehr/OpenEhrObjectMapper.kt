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

package care.better.platform.json.jackson.openehr

import care.better.platform.annotation.Open
import care.better.platform.json.jackson.better.BetterObjectMapper
import care.better.platform.json.jackson.mixedin.BooleanContextExpressionMixedIn
import care.better.platform.json.jackson.rm.RmTypeResolverBuilder
import care.better.platform.json.jackson.time.OpenEhrTimeModule
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.openehr.proc.taskplanning.BooleanContextExpression

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@Open
class OpenEhrObjectMapper : ObjectMapper {
    constructor() : super()
    constructor(src: OpenEhrObjectMapper) : super(src)
    constructor(src: OpenEhrObjectMapper, factory: JsonFactory) : super(src, factory)

    init {
        this.defaultInit()
    }

    override fun copy(): ObjectMapper {
        _checkInvalidCopy(BetterObjectMapper::class.java)
        return OpenEhrObjectMapper(this).also { it.defaultInit() }
    }

    override fun copyWith(factory: JsonFactory): ObjectMapper {
        _checkInvalidCopy(BetterObjectMapper::class.java)
        return OpenEhrObjectMapper(this, factory).also { it.defaultInit() }
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }
}

fun OpenEhrObjectMapper.defaultInit() {
    setDefaultTyping(
        RmTypeResolverBuilder(DefaultTyping.NON_FINAL)
            .init(JsonTypeInfo.Id.CLASS, null)
            .typeProperty("_type")
            .inclusion(JsonTypeInfo.As.PROPERTY))

    registerKotlinModule()
    registerModule(JavaTimeModule())
    registerModule(OpenEhrTimeModule())

    configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    addMixIn(BooleanContextExpression::class.java, BooleanContextExpressionMixedIn::class.java)
    propertyNamingStrategy = OpenEhrPropertyNamingStrategy()
}
