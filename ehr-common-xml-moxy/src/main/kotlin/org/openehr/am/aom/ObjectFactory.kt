package org.openehr.am.aom

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
        private val ARCHETYPE_QNAME = QName("http://schemas.openehr.org/v1", "archetype")
        private val TEMPLATE_QNAME = QName("http://schemas.openehr.org/v1", "template")
    }

    fun createTView(): TView = TView()
    fun createTViewConstraints(): TView.Constraints = TView.Constraints()
    fun createArchetype(): Archetype = Archetype()
    fun createTemplate(): Template = Template()
    fun createSiblingOrder(): SiblingOrder = SiblingOrder()
    fun createCSingleAttribute(): CSingleAttribute = CSingleAttribute()
    fun createCMultipleAttribute(): CMultipleAttribute = CMultipleAttribute()
    fun createCObjectGroup(): CObjectGroup = CObjectGroup()
    fun createCardinality(): Cardinality = Cardinality()
    fun createCComplexObject(): CComplexObject = CComplexObject()
    fun createArchetypeSlot(): ArchetypeSlot = ArchetypeSlot()
    fun createConstraintRef(): ConstraintRef = ConstraintRef()
    fun createCDefinedObject(): CDefinedObject = CDefinedObject()
    fun createCPrimitiveObject(): CPrimitiveObject = CPrimitiveObject()
    fun createArchetypeInternalRef(): ArchetypeInternalRef = ArchetypeInternalRef()
    fun createAssertion(): Assertion = Assertion()
    fun createAssertionVariable(): AssertionVariable = AssertionVariable()
    fun createExprLeaf(): ExprLeaf = ExprLeaf()
    fun createExprUnaryOperator(): ExprUnaryOperator = ExprUnaryOperator()
    fun createExprBinaryOperator(): ExprBinaryOperator = ExprBinaryOperator()
    fun createCBoolean(): CBoolean = CBoolean()
    fun createCString(): CString = CString()
    fun createCInteger(): CInteger = CInteger()
    fun createCReal(): CReal = CReal()
    fun createCDate(): CDate = CDate()
    fun createCDateTime(): CDateTime = CDateTime()
    fun createCTime(): CTime = CTime()
    fun createCDuration(): CDuration = CDuration()
    fun createArchetypeOntology(): ArchetypeOntology = ArchetypeOntology()
    fun createCodeDefinitionSet(): CodeDefinitionSet = CodeDefinitionSet()
    fun createArchetypeTerm(): ArchetypeTerm = ArchetypeTerm()
    fun createTermBindingSet(): TermBindingSet = TermBindingSet()
    fun createTermBindingItem(): TermBindingItem = TermBindingItem()
    fun createConstraintBindingSet(): ConstraintBindingSet = ConstraintBindingSet()
    fun createConstraintBindingItem(): ConstraintBindingItem = ConstraintBindingItem()
    fun createCCodePhrase(): CCodePhrase = CCodePhrase()
    fun createCDvOrdinal(): CDvOrdinal = CDvOrdinal()
    fun createCDvQuantity(): CDvQuantity = CDvQuantity()
    fun createCQuantityItem(): CQuantityItem = CQuantityItem()
    fun createCDvState(): CDvState = CDvState()
    fun createStateMachine(): StateMachine = StateMachine()
    fun createNonTerminalState(): NonTerminalState = NonTerminalState()
    fun createTerminalState(): TerminalState = TerminalState()
    fun createTransition(): Transition = Transition()
    fun createCArchetypeRoot(): CArchetypeRoot = CArchetypeRoot()
    fun createFlatArchetypeOntology(): FlatArchetypeOntology = FlatArchetypeOntology()
    fun createAnnotation(): Annotation = Annotation()
    fun createTConstraints(): TConstraints = TConstraints()
    fun createTAttribute(): TAttribute = TAttribute()
    fun createTComplexObject(): TComplexObject = TComplexObject()
    fun createCCodeReference(): CCodeReference = CCodeReference()
    fun createTViewConstraintsItems(): TView.Constraints.Items = TView.Constraints.Items()

    @XmlElementDecl(namespace = "http://schemas.openehr.org/v1", name = "archetype")
    fun createArchetype(value: Archetype): JAXBElement<Archetype> = JAXBElement(ARCHETYPE_QNAME, Archetype::class.java, null, value)

    @XmlElementDecl(namespace = "http://schemas.openehr.org/v1", name = "template")
    fun createTemplate(value: Template): JAXBElement<Template> = JAXBElement(TEMPLATE_QNAME, Template::class.java, null, value)
}