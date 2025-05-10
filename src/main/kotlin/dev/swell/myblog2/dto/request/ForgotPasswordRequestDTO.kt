package dev.swell.myblog2.dto.request

import dev.swell.myblog2.architecture.validation.UserExistWithEmail
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ForgotPasswordRequestDTO(
    @field:NotBlank
    @field:Email
    @field:UserExistWithEmail
    val email: String
)
