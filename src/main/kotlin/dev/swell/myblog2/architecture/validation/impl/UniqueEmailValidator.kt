package dev.swell.myblog2.architecture.validation.impl

import dev.swell.myblog2.architecture.validation.UniqueEmail
import dev.swell.myblog2.domain.user.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

@Configurable
class UniqueEmailValidator() : ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private lateinit var userService: UserService

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        return value?.let { userService.isEmailRegistered(it) } != true
    }

}
