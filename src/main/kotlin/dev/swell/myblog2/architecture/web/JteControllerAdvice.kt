package dev.swell.myblog2.architecture.web

import dev.swell.myblog2.domain.user.AuthUser
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class JteControllerAdvice {

    @ModelAttribute
    fun csrf(model: Model, request: HttpServletRequest) {
        val csrf: CsrfToken? = request.getAttribute("_csrf") as CsrfToken?
        model.addAttribute("_csrf", csrf)
    }

    @ModelAttribute
    fun authenticatedUser(model: Model) {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        if (auth?.isAuthenticated == true && auth.principal is AuthUser) {
            val user = auth.principal as AuthUser?
            model.addAttribute("auth", user)
        }
    }

}