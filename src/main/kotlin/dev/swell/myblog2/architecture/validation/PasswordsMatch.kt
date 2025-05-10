package dev.swell.myblog2.architecture.validation

import dev.swell.myblog2.architecture.validation.impl.PasswordsMatchValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordsMatchValidator::class])
annotation class PasswordsMatch(
    val message: String = "Passwords do not match",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val passwordField: String = "password",
    val confirmPasswordField: String = "confirmPassword"
)