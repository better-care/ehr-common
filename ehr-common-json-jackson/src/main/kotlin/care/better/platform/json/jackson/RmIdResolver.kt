package care.better.platform.json.jackson

import care.better.openehr.rm.RmObject
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.databind.type.TypeFactory
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import java.io.IOException
import java.util.concurrent.ExecutionException
import kotlin.reflect.KClass

/**
 * @author Primoz Delopst
 */
class RmIdResolver : TypeIdResolver {
    private var baseType: JavaType? = null
    private var typeFactory: TypeFactory? = null

    private val rmClassNames = CacheBuilder.newBuilder()
            .maximumSize(1000L)
            .build(object : CacheLoader<Class<*>?, String?>() {
                override fun load(clazz: Class<*>): String {
                    return RmUtils.getRmTypeName(clazz.kotlin as KClass<out RmObject>)
                }
            })

    constructor() : this(TypeFactory.defaultInstance())


    constructor(typeFactory: TypeFactory)  {
        this.typeFactory = typeFactory
    }

    override fun init(baseType: JavaType?) {
        this.baseType = baseType
    }

    override fun idFromValue(obj: Any): String? {
        return idFromValueAndType(obj, obj.javaClass)
    }

    override fun idFromValueAndType(value: Any?, suggestedType: Class<*>): String? {
        if (RmObject::class.java.isAssignableFrom(suggestedType) || RmObject::class.java.isAssignableFrom(value!!.javaClass)) {
            return try {
                rmClassNames[suggestedType]
            } catch (e: ExecutionException) {
                RmUtils.getRmTypeName(suggestedType.kotlin as KClass<out RmObject>)
            }
        }

        // Should really never happen
        val classNameIdResolver = ClassNameIdResolver(
                typeFactory!!.constructType(value.javaClass), typeFactory, LaissezFaireSubTypeValidator.instance)
        return classNameIdResolver.idFromValueAndType(value, suggestedType)
    }

    override fun idFromBaseType(): String? {
        return idFromValueAndType(null, baseType!!.rawClass)
    }

    @Throws(IOException::class)
    override fun typeFromId(context: DatabindContext?, id: String?): JavaType? {
        return try {
            val rmClass: Class<out RmObject?> = RmUtils.getRmClass(id!!).java
            typeFactory!!.constructType(rmClass)
        } catch (ignored: ClassNotFoundException) {
            // Now try with the regular class type serializer
            val classNameIdResolver = ClassNameIdResolver(
                    null, if (context != null) context.typeFactory else typeFactory, LaissezFaireSubTypeValidator.instance)
            classNameIdResolver.typeFromId(context, id)
        }
    }

    override fun getDescForKnownTypeIds(): String? {
        return null
    }

    override fun getMechanism(): Id? {
        return Id.CUSTOM
    }
}