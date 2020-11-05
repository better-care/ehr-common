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

package care.better.platform.json.jackson.rm

import care.better.openehr.rm.RmObject
import care.better.platform.utils.RmUtils
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

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@Suppress("UNCHECKED_CAST")
class RmIdResolver(private val typeFactory: TypeFactory) : TypeIdResolver {
    private var baseType: JavaType? = null

    private val rmClassNames = CacheBuilder.newBuilder()
        .maximumSize(1000L)
        .build(object : CacheLoader<Class<*>?, String?>() {
            override fun load(clazz: Class<*>): String {
                return RmUtils.getRmTypeName(clazz as Class<out RmObject>)
            }
        })


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
                RmUtils.getRmTypeName(suggestedType as Class<out RmObject>)
            }
        }

        val classNameIdResolver = ClassNameIdResolver(typeFactory.constructType(value.javaClass), typeFactory, LaissezFaireSubTypeValidator.instance)
        return classNameIdResolver.idFromValueAndType(value, suggestedType)
    }

    override fun idFromBaseType(): String? {
        return idFromValueAndType(null, baseType!!.rawClass)
    }

    @Throws(IOException::class)
    override fun typeFromId(context: DatabindContext?, id: String?): JavaType? {
        return try {
            val rmClass: Class<*> = RmUtils.getRmClass(id!!)
            typeFactory.constructType(rmClass)
        } catch (ignored: ClassNotFoundException) {
            val classNameIdResolver = ClassNameIdResolver(
                null,
                if (context != null)
                    context.typeFactory
                else
                    typeFactory,
                LaissezFaireSubTypeValidator.instance)
            classNameIdResolver.typeFromId(context, id)
        }
    }

    override fun getDescForKnownTypeIds(): String? = null


    override fun getMechanism(): Id? = Id.CUSTOM
}
