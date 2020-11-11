package care.better.platform.annotation

/**
 * @author Primoz Delopst
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenEhrName(val name: String)