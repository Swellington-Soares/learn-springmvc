package dev.swell.myblog2.architecture.web

import gg.jte.support.LocalizationSupport
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*


@Configuration
class LocalizationConfig {

    @Bean
    fun messageSource(): MessageSource {
        return ResourceBundleMessageSource().apply {
            setBasenames("i18n.messages")
            setDefaultEncoding("UTF-8")
            setUseCodeAsDefaultMessage(true)
        }
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        return CookieLocaleResolver().apply {
            setDefaultLocale(Locale.ENGLISH)
            defaultTimeZone = TimeZone.getTimeZone("UTC")
        }
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        return LocaleChangeInterceptor().apply {
            paramName = "lang"
        }
    }

    @Component
    class JteMessageLocale(private val messageSource: MessageSource) : LocalizationSupport {
        override fun lookup(key: String): String {
            val locale = LocaleContextHolder.getLocale()
            return messageSource.getMessage(key, null, locale)
        }
    }

    @Component
    class ModelLocalizationInterceptor(private val localizationSupport: JteMessageLocale) : HandlerInterceptor {
        override fun postHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any,
            modelAndView: ModelAndView?
        ) {
            modelAndView?.model?.put("i18n", localizationSupport)
        }
    }

}