/* Copyright 2025 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package care.better.platform.kotlin.serialization.polymorphism.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.classgraph.ClassGraph
import io.github.classgraph.ScanResult
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.modules.SerializersModule
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * @author Primoz Delopst
 * @since 4.2.2
 *
 * Generates polymorphic [SerializersModule] based on GeneratePolymorphicSerializerModule annotation.
 */
class KotlinSerializationPolymorphicGenerator(private val codeGenerator: CodeGenerator) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver
            .getSymbolsWithAnnotation("care.better.platform.kotlin.serialization.polymorphism.api.GeneratePolymorphicSerializerModule")
            .toList()
            .filterIsInstance<KSClassDeclaration>()
            .forEach { annotated ->
                val annotation = annotated.annotations.first {
                    it.annotationType.resolve().declaration.qualifiedName?.asString() == "care.better.platform.kotlin.serialization.polymorphism.api.GeneratePolymorphicSerializerModule"
                }

                val baseClassDecl =
                    (annotation.arguments.firstOrNull { it.name?.asString() == "baseClass" }?.value as? KSType)?.declaration as? KSClassDeclaration
                        ?: throw IllegalArgumentException("Base classes not found.")

                val allowedPackages = (annotation.arguments.firstOrNull { it.name?.asString() == "allowedPackages" }?.value as? List<*>)
                    ?.mapNotNull { it as? String }?.distinct() ?: emptyList()

                val classHierarchy: ClassHierarchy = ClassGraph()
                    .acceptPackages(*allowedPackages.toTypedArray())
                    .enableClassInfo()
                    .scan().use { scanResult ->
                        buildClassHierarchy(
                            scanResult,
                            Class.forName(baseClassDecl.qualifiedName!!.asString()).kotlin
                        )
                    }

                val fileSpec = FileSpec.builder(annotated.packageName.asString(), "${classHierarchy.kClass.simpleName}PolymorphicSerializersModuleProvider")
                    .also { fileSpecBuilder ->
                        classHierarchy.flatten().forEach { fileSpecBuilder.addImport(it.asClassName().packageName, it.asClassName().simpleName) }
                    }
                    .addImport("kotlinx.serialization.modules", "SerializersModule")
                    .addImport("kotlinx.serialization", "serializer")
                    .addImport("kotlinx.serialization.modules", "polymorphic")
                    .addImport("kotlinx.serialization", "KSerializer")
                    .addImport("kotlin.reflect.full", "createType")
                    .addType(
                        TypeSpec.objectBuilder("${classHierarchy.kClass.simpleName}PolymorphicSerializersModuleProvider")
                            .addAnnotation(AnnotationSpec.builder(Suppress::class).addMember("%S", "UNCHECKED_CAST").build())
                            .addProperty(
                                PropertySpec.builder("module", SerializersModule::class)
                                    .initializer(buildString {
                                        append("SerializersModule {").append("\n")
                                        if (classHierarchy.children.isNotEmpty()) {
                                            generatePolymorphicStructure(classHierarchy)
                                        }
                                        append("}")
                                    })
                                    .build()
                            )
                            .build()
                    )
                    .build()

                fileSpec.writeTo(codeGenerator, Dependencies(true))
            }

        return emptyList()
    }

    private fun StringBuilder.generatePolymorphicStructure(classHierarchy: ClassHierarchy) {
        if (classHierarchy.children.isNotEmpty()) {
            classHierarchy.kClass.simpleName.also { name ->
                append("polymorphic($name::class) {").append("\n")
            }
            classHierarchy.getConcreteSubclasses().forEach { concreteSubclass ->
                concreteSubclass.simpleName.also { name ->
                    append("    ").append("subclass($name::class, serializer($name::class.createType()) as KSerializer<$name>)").append("\n")
                }
            }
            append("}").append("\n")
            classHierarchy.children.forEach { child ->
                generatePolymorphicStructure(child)
            }
        }
    }

    internal data class ClassHierarchy(val kClass: KClass<*>, val children: List<ClassHierarchy> = emptyList()) {

        fun getConcreteSubclasses(): List<KClass<*>> =
            children.flatMap { listOfNotNull(it.kClass.takeIf { kClass -> !kClass.isAbstract && !kClass.java.isInterface }) + it.getConcreteSubclasses() }

        fun flatten(): List<KClass<*>> = listOf(kClass) + children.flatMap { it.flatten() }
    }


    @Suppress("UNCHECKED_CAST")
    private fun buildClassHierarchy(scanResult: ScanResult, parent: KClass<*>): ClassHierarchy {
        val children: List<ClassHierarchy> = scanResult
            .getSubclasses(parent.java.name)
            .filter { it.superclass?.name == parent.java.name }
            .map { buildClassHierarchy(scanResult, it.loadClass().kotlin) }

        val modifiedChildren: List<ClassHierarchy> =
            if (parent.isOpen && !parent.isAbstract && !parent.java.isInterface) {
                children + if (parent.findAnnotation<Polymorphic>() != null) listOf(ClassHierarchy(parent, emptyList())) else emptyList()
            } else {
                children
            }

        return ClassHierarchy(kClass = parent, children = modifiedChildren)
    }
}
