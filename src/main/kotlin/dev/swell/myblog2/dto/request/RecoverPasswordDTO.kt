package dev.swell.myblog2.dto.request

import dev.swell.myblog2.architecture.validation.PasswordsMatch
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@PasswordsMatch(passwordField = "password", confirmPasswordField = "confirmPassword")
data class RecoverPasswordDTO(

    @field:NotBlank
    @field:Size(min = 8, max = 20)
    val password: String,

    @field:NotBlank
    @field:Size(min = 8, max = 20)
    val confirmPassword: String
) {
    companion object Factory {
        fun default() = RecoverPasswordDTO("", "")
    }
}
