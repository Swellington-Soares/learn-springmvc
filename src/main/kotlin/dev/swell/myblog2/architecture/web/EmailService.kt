package dev.swell.myblog2.architecture.web

import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service


@Service
class EmailService(
    private val sender: JavaMailSender,
    private val templateEngine: TemplateEngine
) {

    fun sendSimpleEmail(to: String, subject: String, message: String) {
        sender.send(SimpleMailMessage().apply {
            setTo(to)
            setSubject(subject)
            text = message
        })
    }

    fun sendHTMLEmail(
        to: String,
        subject: String,
        template: String,
        params: Map<String, Any>
    ) {
        val output = StringOutput()
        templateEngine.render("$template.kte", params, output)
        val preparator = MimeMessagePreparator { mimeMessage: MimeMessage? ->
            mimeMessage!!.setRecipient(Message.RecipientType.TO, InternetAddress(to))
            mimeMessage.subject = subject
            mimeMessage.setContent(output.toString(), "text/html; charset=utf-8")
        }
        sender.send(preparator);
    }

}