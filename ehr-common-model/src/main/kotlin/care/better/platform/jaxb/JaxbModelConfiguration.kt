package care.better.platform.jaxb

/**
 * @author Marko Pipan
 * @since 4.3.0
 */
object JaxbModelConfiguration {

    /**
     * Should the 'match_negated' elements be removed from the serialized OPT template XML.
     * To remain compatible with older versions of the library, this is false by default.
     */
    @JvmStatic
    var removeMatchNegatedOnSerialization: Boolean = false
}