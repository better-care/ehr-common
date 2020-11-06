package care.better.platform.json.kotlin

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import org.openehr.rm.composition.Composition

/**
 * @author Primoz Delopst
 */


@InternalSerializationApi
fun main() {

    val serializer = Composition::class.serializer()
}
class Serializer {

}