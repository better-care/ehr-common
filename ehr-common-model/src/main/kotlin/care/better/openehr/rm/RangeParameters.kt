package care.better.openehr.rm

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
interface RangeParameters {
    fun isLowerIncluded(): Boolean?

    fun isUpperIncluded(): Boolean?

    fun isLowerUnbounded(): Boolean

    fun isUpperUnbounded(): Boolean
}
