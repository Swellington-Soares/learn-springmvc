package dev.swell.myblog2.architecture.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomAuthFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {

        when (exception) {
            is DisabledException -> {
                response?.sendRedirect("/disabled-account")
            }
//            is BadCredentialsException,
//            is UsernameNotFoundException -> {
//
//            }
            is LockedException -> {
                val username = request?.getParameter("email")
                val url = "/auth/locked-account?email=$username"
                response?.sendRedirect(url)
            }
            else -> response?.sendRedirect("/auth/login?error")
        }
    }
}