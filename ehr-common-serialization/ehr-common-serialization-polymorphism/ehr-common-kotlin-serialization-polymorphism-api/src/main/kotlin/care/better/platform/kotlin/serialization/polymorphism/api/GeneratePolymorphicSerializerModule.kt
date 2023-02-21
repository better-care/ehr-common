package care.better.platform.kotlin.serialization.polymorphism.api

import kotlin.reflect.KClass

/**
 * @author Primoz Delopst
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class GeneratePolymorphicSerializerModule(
    val baseClass: KClass<*> = Any::class,
    val allowedPackages: Array<String> = [])