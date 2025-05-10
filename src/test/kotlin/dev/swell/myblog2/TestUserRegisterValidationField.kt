package dev.swell.myblog2

import dev.swell.myblog2.dto.request.RegisterUserDTO
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertTrue


@SpringBootTest
class TestUserRegisterValidationField {

    private lateinit var validator: Validator

    private lateinit var appUserTestRegister: RegisterUserDTO

    @BeforeEach
    fun beforeStart(){
        validator = Validation.buildDefaultValidatorFactory().validator
        appUserTestRegister = RegisterUserDTO(
            email ="sw@dev.net",
            username = "jose",
            password ="123456",
            firstname = "Swellington",
            lastname  = "Soares"
        )
    }


    @Test
    fun `test validation fail if username is blank`(){
        val registerDTO = appUserTestRegister.copy(
            username = ""
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "username" } }
    }

    @Test
    fun `test validation fail if email is blank`(){
        val registerDTO = appUserTestRegister.copy(
            email = ""
        )

        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "email" } }
    }

    @Test
    fun `test validation fail if password is blank`(){
        val registerDTO = appUserTestRegister.copy(
            password = ""
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "password" } }
    }

    @Test
    fun `test validation fail if firstname is blank`(){
        val registerDTO = appUserTestRegister.copy(
            firstname = ""
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "firstname" } }
    }

    @Test
    fun `test validation fail if lastname is blank`(){
        val registerDTO = appUserTestRegister.copy(
            lastname = ""
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "lastname" } }
    }

    @Test
    fun `test validation fail if username is below of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            username = "sw"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "username" } }
    }

    @Test
    fun `test validation fail if username is above of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            username = "SwellingtonDosSantosSoares"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "username" } }
    }

    @Test
    fun `test validation fail if password is below of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            password = "sw"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "password" } }
    }

    @Test
    fun `test validation fail if password is above of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            password = "SwellingtonDosSantosSoares"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "password" } }
    }

    @Test
    fun `test validation fail if firstname is below of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            firstname = "sw"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "firstname" } }
    }

    @Test
    fun `test validation fail if firstname is above of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            firstname = "SwellingtonDosSantosSoaresSwellingtonDosSantosSoares"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "firstname" } }
    }

    @Test
    fun `test validation fail if lastname is below of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            lastname = "sw"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "lastname" } }
    }

    @Test
    fun `test validation fail if lastname is above of character limit`(){
        val registerDTO = appUserTestRegister.copy(
            lastname = "SwellingtonDosSantosSoaresSwellingtonDosSantosSoares "
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "lastname" } }
    }


    @Test
    fun `test validation fail if email is not a email`(){
        val registerDTO = appUserTestRegister.copy(
            email = "sw.@net.com"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "email" } }
    }

    @Test
    fun `test validation fail if firstname is not a firstname`(){
        val registerDTO = appUserTestRegister.copy(
            firstname = "1soares"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "firstname"  } }
    }

    @Test
    fun `test validation fail if lastname is not a lastname`(){
        val registerDTO = appUserTestRegister.copy(
            lastname = "1soares"
        )
        val violations = validator.validate(registerDTO)
        assertTrue { violations.any { it.propertyPath.toString() == "lastname"  } }
    }

}