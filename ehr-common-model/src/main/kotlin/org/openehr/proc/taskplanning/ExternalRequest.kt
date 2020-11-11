package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class ExternalRequest() : DispatchableAction() {

    lateinit var organisation: PartyProxy
    lateinit var requestId: String
    var otherDetails: ItemStructure? = null

    constructor(organisation: PartyProxy, requestId: String) : this() {
        this.organisation = organisation
        this.requestId = requestId
    }

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): ExternalRequest =
            super.addSubjectPrecondition(subjectPrecondition) as ExternalRequest

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ExternalRequest{" +
                    "organisation=$organisation" +
                    ", requestId='$requestId'" +
                    ", otherDetails=$otherDetails" +
                    "} ${super.toString()}"
}