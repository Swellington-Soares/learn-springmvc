package dev.swell.myblog2.architecture.web

import jakarta.validation.Validator
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory

@Configuration
class ValidationConfig(
    private val beanFactory: AutowireCapableBeanFactory
) {

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        val factoryBean = LocalValidatorFactoryBean()
        factoryBean.constraintValidatorFactory = SpringConstraintValidatorFactory(beanFactory)
        return factoryBean
    }
}
