package care.better.openehr.terminology

/**
 * @author Primoz Delopst
 */

class TermGroup {
    lateinit var groupTermCode: String
    val termCodes: MutableList<String> = mutableListOf()
}