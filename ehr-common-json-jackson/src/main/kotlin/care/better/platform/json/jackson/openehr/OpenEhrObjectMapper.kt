package care.better.platform.json.jackson.openehr

import care.better.platform.json.jackson.rm.RmTypeResolverBuilder
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author Primoz Delopst
 */

class OpenEhrObjectMapper : ObjectMapper() {
    init {
        setDefaultTyping(RmTypeResolverBuilder(DefaultTyping.NON_FINAL)
                                 .init(JsonTypeInfo.Id.CLASS, null)
                                 .typeProperty("_type")
                                 .inclusion(JsonTypeInfo.As.PROPERTY))

        registerModule(KotlinModule())
        configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        propertyNamingStrategy = OpenEhrPropertyNamingStrategy()
    }

}