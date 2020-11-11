package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotEmpty
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

open class ArchetypeOntology : AmObject(), Serializable {
    @RequiresNotEmpty
    var termDefinitions: MutableList<CodeDefinitionSet> = mutableListOf()
    var constraintDefinitions: MutableList<CodeDefinitionSet> = mutableListOf()
    var termBindings: MutableList<TermBindingSet> = mutableListOf()
    var constraintBindings: MutableList<ConstraintBindingSet> = mutableListOf()
}