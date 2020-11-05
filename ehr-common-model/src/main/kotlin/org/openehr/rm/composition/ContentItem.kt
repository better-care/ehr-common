package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class ContentItem : Locatable()