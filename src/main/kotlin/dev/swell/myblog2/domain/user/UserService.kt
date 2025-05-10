package dev.swell.myblog2.domain.user

import dev.swell.myblog2.architecture.exception.VerifyTokenHasExpiredException
import dev.swell.myblog2.architecture.exception.VerifyTokenInvalidTypeException
import dev.swell.myblog2.architecture.exception.VerifyTokenNotFoundException
import dev.swell.myblog2.architecture.token.VerifyToken
import dev.swell.myblog2.architecture.token.VerifyTokenService
import dev.swell.myblog2.architecture.token.VerifyTokenType
import dev.swell.myblog2.architecture.web.EmailService
import dev.swell.myblog2.dto.request.RegisterUserDTO
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull


@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper,
    private val verifyTokenService: VerifyTokenService,
    private val emailService: EmailService
) {

    private fun createUser(user: AppUser): AppUser {
        val password = passwordEncoder.encode(user.password)
        user.password = password
        return user
    }

    fun createUser(user: RegisterUserDTO): AppUser {
        return createUser(userMapper.toEntity(user))
    }

    fun createUser(
        email: String,
        username: String,
        password: String,
        avatar: String? = null,
        firstname: String,
        lastname: String,
        emailVerified: Boolean = false,
    ): AppUser {
        return createUser(
            AppUser(
                email = email,
                username = username,
                password = password,
                avatar = avatar,
                firstname = firstname,
                lastname = lastname,
                emailVerified = emailVerified
            )
        )
    }

    fun sendAccountActivationLinkToEmail(user: AppUser) {
        val token = verifyTokenService.createEmailToken(user, LocalDateTime.now().plusHours(1))

        val link = "http://localhost:8080/auth/verify-account/${token.token}"

        val data = mapOf(
            "name" to "${user.firstname} ${user.lastname}",
            "email" to user.email,
            "link" to link
        )
        emailService.sendHTMLEmail(user.email, "Ativação da conta", "email/email-verify", data)
    }

    fun resendActivationAccountLinkToEmail(email: String) {
        val user = userRepository.findByEmail(email)
        if (user.isPresent && !user.get().emailVerified) {
            sendAccountActivationLinkToEmail(user.get())
        }
    }

    fun register(user: RegisterUserDTO): AppUser {
        val appUser = createUser(user)
        val savedUser = userRepository.save(appUser)
        sendAccountActivationLinkToEmail(savedUser)
        return appUser
    }

    fun findUserByEmail(email: String): Optional<AppUser> {
        return userRepository.findByEmail(email)
    }

    fun isUsernameRegistered(username: String) = userRepository.existsByUsername(username)

    fun isEmailRegistered(email: String) = userRepository.existsByEmail(email)

    fun activateAccount(tokenText: String) {
        val token = verifyTokenService.findTokenInfoByTokenIdentify(tokenText).orElseThrow {
            VerifyTokenNotFoundException()
        }
        if (token.isExpired()) throw VerifyTokenHasExpiredException()
        if (!token.isActiveAccountType()) throw VerifyTokenInvalidTypeException()
        val user = token.user
        user.emailVerified = true
        userRepository.save(user)
        verifyTokenService.deleteToken(token)
    }

    fun recoverPassword(tokenText: String, password: String) {
        val token = verifyTokenService.findTokenInfoByTokenIdentify(tokenText).orElseThrow {
            VerifyTokenNotFoundException()
        }
        if (token.isExpired()) throw VerifyTokenHasExpiredException()
        if (!token.isRecoverPasswordType()) throw VerifyTokenInvalidTypeException()
        val user = token.user
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)
        verifyTokenService.deleteToken(token)
    }

    fun sendRecoverPasswordEmailToUser(email: String) {
        val optUser = userRepository.findByEmail(email)
        if (!optUser.isPresent) return;
        val user = optUser.get()
        val token = verifyTokenService.findUseTokenByType(user, VerifyTokenType.RECOVER_PASSWORD_TOKEN).getOrElse {
            verifyTokenService.createRecoverPasswordToken(user, LocalDateTime.now().plusHours(48))
        }
        if (token.isExpired()) {
            token.expireIn = LocalDateTime.now().plusHours(48)
            verifyTokenService.updateTokenExpireIn( token.token, token.expireIn )
        }

        val link = "http://localhost:8080/auth/recover-password/${token.token}"

        val data = mapOf(
            "name" to "${user.firstname} ${user.lastname}",
            "email" to user.email,
            "link" to link
        )
        emailService.sendHTMLEmail(user.email, "Recuperação da Senha", "email/recover-password", data)
    }

}