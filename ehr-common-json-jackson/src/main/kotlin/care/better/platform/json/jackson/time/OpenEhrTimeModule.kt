package care.better.platform.json.jackson.time

import care.better.platform.time.temporal.OpenEhrTemporal
import care.better.platform.web.template.converter.json.serializers.OpenEhrSerializer
import com.fasterxml.jackson.core.util.VersionUtil
import com.fasterxml.jackson.databind.module.SimpleModule

/**
 * @author Matic Ribic
 */
class OpenEhrTimeModule() : SimpleModule(VERSION) {

    companion object {
        private val VERSION = VersionUtil.parseVersion("1.0.0", "care.better.platform", "open-ehr-time-common")
    }

    init {
        addSerializer(OpenEhrTemporal::class.java, OpenEhrSerializer())
    }
}