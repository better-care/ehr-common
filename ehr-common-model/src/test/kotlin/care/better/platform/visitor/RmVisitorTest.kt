package care.better.platform.visitor

import care.better.openehr.rm.RmObject
import com.google.common.base.CaseFormat
import org.assertj.core.api.Assertions.assertThat
import org.openehr.rm.common.Locatable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Tests that verify all RmObject classes with visit methods properly visit all their properties
 */
open class RmVisitorTest {

    /**
     * A visitor context that tracks which properties have been visited
     */
    class TrackingVisitorContext : RmVisitorContext {
        val visitedProperties = mutableMapOf<Any, MutableSet<String>>()

        override fun beforeLocatable(attributeName: String, locatable: Locatable, typeName: String): Boolean {
            return true
        }

        override fun afterLocatable(attributeName: String, locatable: Locatable, typeName: String) {
            // Nothing to do
        }

        override fun beforeObject(attributeName: String, value: RmObject, typeName: String): Boolean {
            return true
        }

        override fun afterObject(attributeName: String, value: RmObject, typeName: String) {
            // Nothing to do
        }

        override fun visitValue(attributeName: String, value: Any, owner: Any) {
            visitedProperties.computeIfAbsent(owner) { mutableSetOf() }.add(attributeName)
        }

        fun wasPropertyVisited(owner: Any, propertyName: String): Boolean {
            return visitedProperties[owner]?.contains(propertyName) == true
        }
    }

    /**
     * Helper function to check if a property contains an RmObject
     */
    private fun isRmObjectProperty(obj: Any, prop: KProperty1<out Any, *>): Boolean {
        prop.isAccessible = true
        val value = prop.getter.call(obj)
        return value is RmObject ||
            (value is Collection<*> && value.any { it is RmObject })
    }

    /**
     * Base test method that validates visit implementation for any RmObject
     */
    fun <T : RmObject> validateVisit(obj: T) {
        val context = TrackingVisitorContext()

        // Call visit
        val visitMethod = obj::class.members.find { it.name == "visit" }
        assertThat(visitMethod).isNotNull()
        visitMethod?.call(obj, "test", context)

        // Get all properties that should have been visited (primitive types)
        val allProperties = obj::class.memberProperties
            .filter { it.name != "class" }
            .filter { prop -> !isRmObjectProperty(obj, prop) }

        // For each primitive property, check if it was visited when it has a non-null value
        allProperties.forEach { prop ->
            prop.isAccessible = true
            val value = prop.getter.call(obj)

            if (value != null && value !is Collection<*>) {
                val serialName = prop.annotations
                    .filterIsInstance<kotlinx.serialization.SerialName>()
                    .firstOrNull()?.value
                    ?: caseConverter.convert(prop.name)!!

                assertThat(context.wasPropertyVisited(obj, serialName))
                    .withFailMessage(
                        "Property '${prop.name}' (serialized as '$serialName') was not visited for ${obj::class.simpleName}"
                    )
                    .isTrue()
            }
        }
    }

    companion object {
        private val caseConverter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE)
    }
}
