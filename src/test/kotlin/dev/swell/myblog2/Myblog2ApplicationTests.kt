package dev.swell.myblog2

import dev.swell.myblog2.domain.user.UserMapper
import dev.swell.myblog2.domain.user.UserRepository
import dev.swell.myblog2.domain.user.UserService
import dev.swell.myblog2.dto.request.RegisterUserDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@SpringBootTest
class Myblog2ApplicationTests() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userMapper: UserMapper

    @Test
    fun `context loads`() {
        println("Context started successfully")
    }

    @Test
    fun `check if create a user instance successfully`() {
        assertNotNull(
            userService.createUser(
                "sw@dev.net",
                "swellington",
                "123456",
                null,
                "Swellington",
                "Soares"
            )
        )
    }

    @Test
    fun `check if user mapper work correctly`() {
        val userDTO = RegisterUserDTO(
            email ="sw@dev.net",
            username = "swellington",
            password ="123456",
            firstname = "Swellington",
            lastname  = "Soares"
        )

       val result = assertDoesNotThrow { userMapper.toEntity( userDTO ) }
       assertEquals( userDTO.email, result.email )
    }

    @Test
    fun `check if user password is encoded correctly`() {
        val userDTO = RegisterUserDTO(
            email ="sw@dev.net",
            username = "swellington",
            password ="123456",
            firstname = "Swellington",
            lastname  = "Soares"
        )

        val user = userService.createUser(userDTO)

        assertTrue { passwordEncoder.matches(userDTO.password, user.password) }
    }


}
