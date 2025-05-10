package dev.swell.myblog2.architecture.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.DataSource


@Configuration
class SecurityConfig {

    @Value("\${spring.security.rememberme.key}")
    private lateinit var rememberMeKey: String

    @Value("\${spring.security.rememberme.token-max-time-in-seconds}")
    private var remeberTokenExpireTime: Int = 604800


    private val STATIC_RESOURCES = listOf(
        "css/**", "js/**", "img/**", "font/**", "assets/**", "images/**", "/favicon.ico"
    )

    private val NOT_NEED_AUTH = listOf(
        "/error/**", "/disabled-account"
    )

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, authFailureHandler: CustomAuthFailureHandler, tokenRepository: PersistentTokenRepository
    ): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.requestMatchers(
                "/auth/login",
                "/register",
                "/auth/locked-account",
                "/auth/forgot-password",
                "/auth/recover-password/{token}",
                "/verify-account/{token}",
                "/auth/verify-account/{token}"
            ).anonymous().requestMatchers(*STATIC_RESOURCES.toTypedArray()).permitAll()
                .requestMatchers(*NOT_NEED_AUTH.toTypedArray()).permitAll().anyRequest().authenticated()
        }.formLogin {
            it.usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/auth/login")
                .failureHandler(authFailureHandler)
                .permitAll()
        }.rememberMe {
            it.tokenRepository(tokenRepository).rememberMeParameter("remember")
                .tokenValiditySeconds(remeberTokenExpireTime) //7 days
                .key(rememberMeKey)
        }.logout {
            it.logoutUrl("/auth/logout")
                .logoutSuccessUrl("/?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", "remember-me")
        }

        return http.build()
    }


    @Bean
    fun authenticationManager(
        userDetailsService: CustomUserDetailsService, passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider(passwordEncoder)
//        authenticationProvider.setPreAuthenticationChecks { preAuthentication ->
//            when {
//                !preAuthentication.isAccountNonLocked -> throw UserAccountNotVerified()
//                !preAuthentication.isEnabled -> throw UserAccountBanned()
//            }
//        }
        authenticationProvider.setUserDetailsService(userDetailsService)
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun persistentTokenRepository(dataSource: DataSource): PersistentTokenRepository {
        return JdbcTokenRepositoryImpl().apply {
            setDataSource(dataSource)
        }
    }


}

