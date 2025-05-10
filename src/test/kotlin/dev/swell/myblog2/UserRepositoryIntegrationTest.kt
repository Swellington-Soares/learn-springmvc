package dev.swell.myblog2

import dev.swell.myblog2.domain.user.AppUser
import dev.swell.myblog2.domain.user.UserRepository
import dev.swell.myblog2.domain.user.UserService
import dev.swell.myblog2.dto.request.RegisterUserDTO
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.springframework.beans.factory.annotation.Autowired
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import kotlin.test.assertTrue

@SpringBootTest
@Testcontainers
class UserRepositoryIntegrationTest {

    companion object {
        @Container
        val mariaDB = MariaDBContainer("mariadb:latest").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
        }
    }

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userService: UserService

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
    fun `test if user is saved`() {
        userRepository.save(appUser)
        val found = userRepository.findByEmail(appUser.email)
        assert(found.isPresent)
        assert(found.get().username == appUser.username)
    }

    @Test
    @Transactional
    fun `test if user is saved by user service registered`() {

        val dto = RegisterUserDTO(
            appUser.username,
            appUser.password,
            appUser.email,
            appUser.firstname,
            appUser.lastname
        )


        val user = userService.register( dto )

        assertTrue { user.id != null }
    }

    @Test
    @Transactional
    fun `test if createdAt field is assigned`() {
        val user = appUser.copy()
        userRepository.save(user)
        val found = userRepository.findByEmail(appUser.email)
        assert(found.isPresent)
        val savedUser = found.get()
        assertTrue { LocalDateTime.now() > savedUser.createdAt }
    }

}
