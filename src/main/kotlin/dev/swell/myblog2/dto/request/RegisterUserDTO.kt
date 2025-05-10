package dev.swell.myblog2.dto.request

import dev.swell.myblog2.architecture.validation.UniqueEmail
import dev.swell.myblog2.architecture.validation.UniqueUsername
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterUserDTO(

    @field:NotBlank
    @field:Size(min = 4, max = 20)
    @field:UniqueUsername
    val username: String,

    @field:NotBlank
    @field:Size(min = 8, max = 20)
    val password: String,

    @field:NotBlank
    @field:Email
    @field:UniqueEmail
    val email: String,

    @field:NotBlank
    @field:Size(min = 5, max = 30)
    val firstname: String,

    @field:NotBlank
    @field:Size(min = 5, max = 30)
    val lastname: String

    ) {


    object Factory {
        val default: () -> RegisterUserDTO = {
            RegisterUserDTO("", "", "", "", "")
        }
    }



}
