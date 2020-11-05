package care.better.platform.path

import care.better.platform.annotation.Open
import org.apache.commons.lang3.builder.ToStringBuilder
import java.util.*

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Holds information about single path segment.
 *
 * Note that prefix is `null` when name prefix is used (i.e. /items[at0001 and name/value='Name'] or /items[at0001,'Name']
 *
 * @constructor Creates a new instance of [PathSegment]
 * @param element First part of a path segment (**items**[at0001,'Name'])
 * @param archetypeNodeId Archetype node id part of the archetype predicate (/items[**at0001**,'Name'])
 * @param name Additional condition value (/items[at0001,**'Name'**] or /items[at0001 and uid/value=**'Name'**])
 * @param prefix Additional condition prefix - specified after archetype node (/items[at0001 and **uid/value**='Name']).
 */
@Open
class PathSegment(val element: String?, val archetypeNodeId: String?, val name: String?, val prefix: String?) {

    /**
     * Creates a new instance of [PathSegment].
     *
     * @param element First part of a path segment (**items**[at0001,'Name'])
     * @param archetypeNodeId Archetype node id part of the archetype predicate (/items[**at0001**,'Name'])
     */
    constructor(element: String?, archetypeNodeId: String?) : this(element, archetypeNodeId, null)

    /**
     * Creates a new instance of [PathSegment].
     * @param element First part of a path segment (**items**[at0001,'Name'])
     * @param archetypeNodeId Archetype node id part of the archetype predicate (/items[**at0001**,'Name'])
     * @param name Additional condition value (/items[at0001,**'Name'**] or /items[at0001 and uid/value=**'Name'**])
     */
    constructor(element: String?, archetypeNodeId: String?, name: String?) : this(element, archetypeNodeId, name, null)

    /**
     * Returns [PathSegment] [String].
     *
     * @return [PathSegment] [String]
     */
    fun getSegment(): String =
        if (archetypeNodeId == null)
            PathUtils.underscorePath(element!!)
        else
            "${PathUtils.underscorePath(element!!)}[${archetypeNodeId}]"

    /**
     * Formats this [PathSegment] to be used in AQL paths. Note no leading '/' is added.
     *
     * @return [PathSegment] formatted for use in AQL path
     */
    fun asPathSegment(): String {
        val path = StringBuilder(PathUtils.underscorePath(element!!))
        if (archetypeNodeId != null) {
            path.append('[').append(archetypeNodeId)
            if (name != null) {
                if (prefix == null) {
                    path.append(",'")
                } else {
                    path.append(" and ").append(prefix).append("='")
                }
                path.append(name!!.replace("'", "\\'")).append('\'')
            }
            path.append(']')
        }
        return path.toString()
    }

    /**
     * Determines whether the [PathSegment] specified as other matches (describes same) path as this one,
     * where either one may be less specific (ie. have looser constraints) than the other. This method is commutative.
     *
     * @param other The searching segment that we compare for match.
     * @return [Boolean] indicating if the other segment is compatible with this segment
     */
    fun matchesSegment(other: PathSegment): Boolean =
        when {
            element != other.element -> false
            other.archetypeNodeId != null && archetypeNodeId != null && archetypeNodeId != other.archetypeNodeId -> false
            other.name != null && name != null && name != other.name -> false
            else -> other.prefix == null || prefix == null || prefix == other.prefix
        }

    override fun toString(): String =
        ToStringBuilder(this)
            .append("element", element)
            .append("archetypeNodeId", archetypeNodeId)
            .append("prefix", prefix)
            .append("name", name)
            .toString()

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other == null || javaClass != other.javaClass -> false
            else -> archetypeNodeId == (other as PathSegment).archetypeNodeId
                    && element == other.element
                    && name == other.name
                    && prefix == other.prefix
        }

    override fun hashCode(): Int = Objects.hash(element, archetypeNodeId, prefix, name)
}
