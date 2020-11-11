package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

open class ArchetypeOntology : AmObject(), Serializable {
    var termDefinitions: MutableList<CodeDefinitionSet> = mutableListOf()
    var constraintDefinitions: MutableList<CodeDefinitionSet> = mutableListOf()
    var termBindings: MutableList<TermBindingSet> = mutableListOf()
    var constraintBindings: MutableList<ConstraintBindingSet> = mutableListOf()
}