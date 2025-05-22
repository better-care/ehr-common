package care.better.platform.json.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import java.io.IOException

/**
 * Serializer forces multiline string in YAML if string contains new line characters (\n).
 * When YAML is used, it preprocesses string to make sure it can be presented as a multiline string.
 *
 * YAML changes:<br>
 * - removes carriage return characters (\r)
 * - replaces tabs (\t) with two spaces
 * - removes trailing spaces
 *
 * It does no processing for JSON and simply forwards serialization to StringSerializer.
 * @author Jan Živković
 */
class MultilineStringYamlSerializer : StdSerializer<String>(String::class.java) {
    @Throws(IOException::class)
    override fun serialize(value: String, gen: JsonGenerator, provider: SerializerProvider) =
        serializeInternal(value, gen, provider)

    @Throws(IOException::class)
    override fun serializeWithType(value: String, gen: JsonGenerator, provider: SerializerProvider, typeSer: TypeSerializer) =
        serializeInternal(value, gen, provider)

    @Throws(IOException::class)
    private fun serializeInternal(value: String, gen: JsonGenerator, provider: SerializerProvider) {
        provider.defaultSerializeValue(if (gen is YAMLGenerator) preprocessString(value) else value, gen)
    }

    /**
     * For YAML multiline string to be constructed, the following conditions must be met:
     * - each line must not end with spaces or tabs
     * - text cannot contain carriage return (/r)
     * - text cannot contain tabs (/t)
     *
     * This function replaces tabs with two spaces, removes carriage return and trims
     * trailing spaces (and tabs, as those have been replaced with spaces).
     * @param value the string to preprocess
     * @return string without tabs, carriage return and trimmed spaces
     */
    private fun preprocessString(value: String?) = value?.replace(TAB_REGEX, "  ")
            ?.replace(CARRIAGE_RETURN_REGEX, "")
            ?.replace(TRAILING_SPACES_REGEX, "\n")
            ?.trimEnd()

    companion object {
        private val TAB_REGEX: Regex = Regex("\t")
        private val CARRIAGE_RETURN_REGEX: Regex = Regex("\r")
        private val TRAILING_SPACES_REGEX: Regex = Regex(" +\n")
    }
}