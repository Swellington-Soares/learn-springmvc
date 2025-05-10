package dev.swell.myblog2.architecture.security

import dev.swell.myblog2.domain.user.AuthUser
import dev.swell.myblog2.domain.user.UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val appUser =
            userService.findUserByEmail(email).orElseThrow { UsernameNotFoundException("$email not found") }
        return AuthUser(
            appUser = appUser,
            password = appUser.password,
            username = email,
            authorities = setOf(SimpleGrantedAuthority("ROLE_USER")),
            enabled = !appUser.disabled,
            accountNonLocked = appUser.emailVerified
        )
    }
}


