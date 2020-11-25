package care.better.openehr.rm

/**
 * @author Primoz Delopst
 */
interface RangeParameters {
    fun isLowerIncluded(): Boolean?

    fun isUpperIncluded(): Boolean?

    fun isLowerUnbounded(): Boolean

    fun isUpperUnbounded(): Boolean
}