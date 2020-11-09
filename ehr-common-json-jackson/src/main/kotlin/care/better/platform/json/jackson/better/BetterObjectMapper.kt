package care.better.platform.json.jackson.better

import care.better.platform.json.jackson.RmTypeResolverBuilder
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author Primoz Delopst
 */

class BetterObjectMapper : ObjectMapper() {
    init {
        setDefaultTyping(RmTypeResolverBuilder(DefaultTyping.NON_FINAL).init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY))
        registerModule(KotlinModule())
        configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        propertyNamingStrategy = BetterPlatformPropertyNamingStrategy()
    }
}