package dev.swell.myblog2

import dev.swell.myblog2.domain.user.AppUser
import dev.swell.myblog2.domain.user.UserRepository
import dev.swell.myblog2.domain.user.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.Test


@SpringBootTest
@Testcontainers
class TestUserService {

    companion object {
        @Container
        val mariaDB = MariaDBContainer("mariadb:latest").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
        }
    }

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository



    lateinit var appUser: AppUser

    @BeforeEach
    fun restoreAppUserModel() {
        appUser = userService.createUser(
            "sw@dev.net",
            "swellington",
            "123456",
            null,
            "Swellington",
            "Soares"
        )
    }


    @Test
    @Transactional
    fun `test if recover password generate and send email`(){
        val email = appUser.email

        userRepository.save(appUser)

        assertDoesNotThrow { userService.sendRecoverPasswordEmailToUser(email) }

    }
}