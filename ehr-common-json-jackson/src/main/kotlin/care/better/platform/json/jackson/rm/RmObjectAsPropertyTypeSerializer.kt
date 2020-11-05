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
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.type.WritableTypeId
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeSerializer
import java.io.IOException

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class RmObjectAsPropertyTypeSerializer(idRes: TypeIdResolver?, property: BeanProperty?, propName: String?) :
    AsPropertyTypeSerializer(idRes, property, propName) {

    override fun forProperty(prop: BeanProperty?): AsPropertyTypeSerializer? =
        if (_property === prop)
            this
        else
            RmObjectAsPropertyTypeSerializer(_idResolver, prop, _typePropertyName)

    @Throws(IOException::class)
    override fun writeTypePrefix(g: JsonGenerator?, typeIdDef: WritableTypeId): WritableTypeId? =
        if (isRmObject(typeIdDef.forValue)) {
            super.writeTypePrefix(g, typeIdDef)
        } else {
            _generateTypeId(typeIdDef)
            writeNonRmTypePrefix(g!!, typeIdDef)
        }

    @Throws(IOException::class)
    override fun writeTypeSuffix(g: JsonGenerator?, typeIdDef: WritableTypeId): WritableTypeId? {
        return if (isRmObject(typeIdDef.forValue)) {
            super.writeTypeSuffix(g, typeIdDef)
        } else {
            writeNonRmTypeSuffix(g!!, typeIdDef)
        }
    }

    @Throws(IOException::class)
    fun writeNonRmTypePrefix(g: JsonGenerator, typeIdDef: WritableTypeId): WritableTypeId? {
        val id = typeIdDef.id
        val valueShape = typeIdDef.valueShape
        if (g.canWriteTypeId()) {
            typeIdDef.wrapperWritten = false
            g.writeTypeId(id)
        } else {
            val idStr = if (id is String) id else id.toString()
            typeIdDef.wrapperWritten = true
            var incl = typeIdDef.include
            if (valueShape != JsonToken.START_OBJECT
                && incl.requiresObjectContext()
            ) {
                incl = WritableTypeId.Inclusion.WRAPPER_ARRAY
                typeIdDef.include = incl
            }
            when (incl) {
                WritableTypeId.Inclusion.PARENT_PROPERTY, WritableTypeId.Inclusion.PAYLOAD_PROPERTY, WritableTypeId.Inclusion.METADATA_PROPERTY -> {
                }
                WritableTypeId.Inclusion.WRAPPER_OBJECT -> g.writeStartObject()
                WritableTypeId.Inclusion.WRAPPER_ARRAY -> {
                }
                else -> {
                    g.writeStartArray()
                    g.writeString(idStr)
                }
            }
        }
        // and finally possible start marker for value itself:
        if (valueShape == JsonToken.START_OBJECT) {
            g.writeStartObject(typeIdDef.forValue)
        } else if (valueShape == JsonToken.START_ARRAY) {
            // should we now set the current object?
            g.writeStartArray()
        }
        return typeIdDef
    }

    @Throws(IOException::class)
    fun writeNonRmTypeSuffix(g: JsonGenerator, typeIdDef: WritableTypeId): WritableTypeId? {
        val valueShape = typeIdDef.valueShape
        // First: does value need closing?
        if (valueShape == JsonToken.START_OBJECT) {
            g.writeEndObject()
        } else if (valueShape == JsonToken.START_ARRAY) {
            g.writeEndArray()
        }
        if (typeIdDef.wrapperWritten) {
            when (typeIdDef.include) {
                WritableTypeId.Inclusion.PARENT_PROPERTY ->                     // unusually, need to output AFTER value. And no real wrapper...
                {
                    val id = typeIdDef.id
                    val idStr = if (id is String) id else id.toString()
                    g.writeStringField(typeIdDef.asProperty, idStr)
                }
                WritableTypeId.Inclusion.WRAPPER_ARRAY, WritableTypeId.Inclusion.METADATA_PROPERTY, WritableTypeId.Inclusion.PAYLOAD_PROPERTY -> {
                }
                WritableTypeId.Inclusion.WRAPPER_OBJECT -> g.writeEndObject()
                else -> g.writeEndObject()
            }
        }
        return typeIdDef
    }

    private fun isRmObject(value: Any): Boolean {
        return RmObject::class.java.isAssignableFrom(value.javaClass)
    }
}
