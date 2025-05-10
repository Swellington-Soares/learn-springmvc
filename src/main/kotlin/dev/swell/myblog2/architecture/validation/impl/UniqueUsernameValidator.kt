package dev.swell.myblog2.architecture.validation.impl

import dev.swell.myblog2.architecture.validation.UniqueUsername
import dev.swell.myblog2.domain.user.UserRepository
import dev.swell.myblog2.domain.user.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.jetbrains.kotlin.javax.inject.Inject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.stereotype.Component

@Configurable
class UniqueUsernameValidator() : ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private lateinit var userService: UserService

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean {
        return value?.let { userService.isUsernameRegistered(it) } != true
    }

}
