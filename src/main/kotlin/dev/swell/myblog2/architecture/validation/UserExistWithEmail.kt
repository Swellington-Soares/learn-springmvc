package dev.swell.myblog2.architecture.validation

import dev.swell.myblog2.architecture.validation.impl.UserExistWithEmailValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ UserExistWithEmailValidator::class ])
annotation class UserExistWithEmail(
    val message: String = "User Not Found",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)
