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
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.openehr.proc.taskplanning.BooleanContextExpression

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@Suppress("LeakingThis")
@Open
class BetterObjectMapper : ObjectMapper() {
    init {
        setDefaultTyping(RmTypeResolverBuilder(DefaultTyping.NON_FINAL).init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY))
        registerModule(KotlinModule())
        registerModule(JavaTimeModule())
        configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        addMixIn(BooleanContextExpression::class.java, BooleanContextExpressionMixedIn::class.java)
        propertyNamingStrategy = BetterPlatformPropertyNamingStrategy()
    }
}
