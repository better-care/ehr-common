package org.openehr.rm.composition

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlElementDecl
import javax.xml.bind.annotation.XmlRegistry
import javax.xml.namespace.QName

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    companion object {
        private val COMPOSITION_QNAME = QName("http://schemas.openehr.org/v1", "composition")
    }

    fun createAction(): Action = Action()
    fun createActivity(): Activity = Activity()
    fun createAdminEntry(): AdminEntry = AdminEntry()
    fun createComposition(): Composition = Composition()
    fun createEvaluation(): Evaluation = Evaluation()
    fun createEventContext(): EventContext = EventContext()
    fun createInstruction(): Instruction = Instruction()
    fun createInstructionDetails(): InstructionDetails = InstructionDetails()
    fun createIsmTransition(): IsmTransition = IsmTransition()
    fun createObservation(): Observation = Observation()
    fun createSection(): Section = Section()


    @XmlElementDecl(namespace = "http://schemas.openehr.org/v1", name = "composition")
    fun createComposition(value: Composition): JAXBElement<Composition>? {
        return JAXBElement(COMPOSITION_QNAME, Composition::class.java, null, value)
    }
}