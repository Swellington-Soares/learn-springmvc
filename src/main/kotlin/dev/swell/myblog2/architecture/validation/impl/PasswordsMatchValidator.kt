package dev.swell.myblog2.architecture.validation.impl

import dev.swell.myblog2.architecture.validation.PasswordsMatch
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordsMatchValidator : ConstraintValidator<PasswordsMatch, Any> {

    private lateinit var passwordField: String
    private lateinit var confirmPasswordField: String
    private lateinit var message: String

    override fun initialize(constraintAnnotation: PasswordsMatch) {
        passwordField = constraintAnnotation.passwordField
        confirmPasswordField = constraintAnnotation.confirmPasswordField
        message = constraintAnnotation.message
    }

    override fun isValid(obj: Any?, context: ConstraintValidatorContext): Boolean {
        if (obj == null) return false

        val clazz = obj::class
        val password = clazz.members.find { it.name == passwordField }?.call(obj) as? String
        val confirmPassword = clazz.members.find { it.name == confirmPasswordField }?.call(obj) as? String
        val valid = password == confirmPassword
        if (!valid) {
            context.disableDefaultConstraintViolation()
            context
                .buildConstraintViolationWithTemplate(message)
                .addPropertyNode(confirmPasswordField)
                .addConstraintViolation()
        }
        return valid
    }
}

