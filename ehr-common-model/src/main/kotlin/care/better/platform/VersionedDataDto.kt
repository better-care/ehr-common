package care.better.platform

import org.openehr.rm.common.OriginalVersion
import org.openehr.rm.common.VersionedObject
import org.openehr.rm.composition.Composition
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class VersionedDataDto : Serializable {
    var version: OriginalVersion? = null
    var versionedObject: VersionedObject? = null
    var composition: Composition? = null
}