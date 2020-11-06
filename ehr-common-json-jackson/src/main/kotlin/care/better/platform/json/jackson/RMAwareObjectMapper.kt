package care.better.platform.json.jackson

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder

/**
 * @author Primoz Delopst
 */

class RMAwareObjectMapper : ObjectMapper() {

    private val typeResolverBuilder: TypeResolverBuilder<*> = RmTypeResolverBuilder(DefaultTyping.NON_FINAL)
            .init(JsonTypeInfo.Id.CLASS, null)
            .inclusion(JsonTypeInfo.As.PROPERTY)

    init {
        setDefaultTyping(typeResolverBuilder)

        //registerModule(KotlinModule())

        configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        propertyNamingStrategy = BetterPlatformPropertyNamingStrategy()
    }
}