package dev.swell.myblog2

import dev.swell.myblog2.architecture.web.EmailService
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class TestEmailSender {

    @Autowired
    private lateinit var emailService: EmailService

    @Test
    fun `test if email is sent with confirm account email template correctly`() {
        assertDoesNotThrow {
            emailService.sendHTMLEmail(
                "sw@dev.com",
                "E-mail de ativação de conta",
                "email/email-verify",
                mapOf(
                    "name" to "Swellington Soares",
                    "email" to "sw@dev.com",
                    "link" to "http://link.com"
                )
            )
        }
    }


    @Test
    fun `test if email send simple message method correctly`() {
        assertDoesNotThrow {
            emailService.sendSimpleEmail(
                "sw@dev",
                "Hello Simple Message Text",
                "This is a simple message text from email system. Hi!"

            )
        }
    }


}