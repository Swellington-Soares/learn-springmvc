package dev.swell.myblog2.architecture.web

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.ui.Model
import org.springframework.validation.Validator
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor


@Configuration
class WebConfig(
    private val localeChangeInterceptor: LocaleChangeInterceptor,
    private val modelLocalizationInterceptor: LocalizationConfig.ModelLocalizationInterceptor,
    private val validator: Validator
) : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/disabled-account").setViewName("disabled")
    }

    override fun getValidator(): Validator? = validator

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor)
        registry.addInterceptor(modelLocalizationInterceptor)
    }


}