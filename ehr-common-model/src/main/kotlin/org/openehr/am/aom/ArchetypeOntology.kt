package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class ArchetypeOntology : AmObject() {
    var termDefinitions: MutableList<CodeDefinitionSet> = mutableListOf()
    var constraintDefinitions: MutableList<CodeDefinitionSet> = mutableListOf()
    var termBindings: MutableList<TermBindingSet> = mutableListOf()
    var constraintBindings: MutableList<ConstraintBindingSet> = mutableListOf()
}