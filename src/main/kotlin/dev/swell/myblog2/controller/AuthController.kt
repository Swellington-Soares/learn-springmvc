package dev.swell.myblog2.controller

import dev.swell.myblog2.architecture.token.VerifyTokenService
import dev.swell.myblog2.domain.user.UserService
import dev.swell.myblog2.dto.request.ForgotPasswordRequestDTO
import dev.swell.myblog2.dto.request.RecoverPasswordDTO
import dev.swell.myblog2.dto.response.AlertMessage
import dev.swell.myblog2.dto.response.AlertMessageType
import jakarta.validation.constraints.Email
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/auth")
class AuthController(private val userService: UserService, private val verifyTokenService: VerifyTokenService) {

    @GetMapping("/login")
    fun loginView(
        @RequestParam("error", required = false) error: String?,
        model: Model,
    ): String {
        model.addAttribute("error", error)
        return "auth/login-view"
    }


    @GetMapping("/forgot-password")
    fun passwordForgotView(): String {
        return "auth/password-recover-view"
    }


    @PostMapping("/forgot-password")
    fun requestPasswordForgot(
        @ModelAttribute("email") @Validated email: ForgotPasswordRequestDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {

        if (!bindingResult.hasErrors()) {
            userService.sendRecoverPasswordEmailToUser(email.email)
        }

        redirectAttributes.addFlashAttribute(
            "alert",
            AlertMessage(
                "Se o email existir, um link para a recuperação será enviado.",
                AlertMessageType.INFO
            )
        )

        return "redirect:/auth/login"
    }

    @GetMapping("/recover-password/{token}")
    fun recoverPasswordView(
        @PathVariable("token", required = true) token: String,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (!verifyTokenService.isTokenValid(token)) {
            redirectAttributes.addFlashAttribute(
                "alert",
                AlertMessage(
                    "Token Expirado.",
                    AlertMessageType.ERROR
                )
            )
            return "redirect:/auth/login"
        }

        model.addAttribute("token", token)
        model.addAttribute("passwordForm", RecoverPasswordDTO.default())
        return "auth/password-recover-view"
    }

    @PostMapping("/recover-password/{token}")
    fun recoverPassword(
        @PathVariable("token", required = true) token: String,
        @ModelAttribute("passwordForm") @Validated form: RecoverPasswordDTO,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {

        if (bindingResult.hasErrors()) {
            val errors = bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
            model.addAttribute("errors", errors)
            model.addAttribute("passwordForm", RecoverPasswordDTO.default())
            return "auth/password-recover-view"
        }
        try {
            userService.recoverPassword(token, form.password)
            redirectAttributes.addFlashAttribute(
                "alert", AlertMessage(
                    "Senha alterada com sucesso.",
                    AlertMessageType.SUCCESS
                )
            )
        } catch (ex: Exception) {
            redirectAttributes.addFlashAttribute(
                "alert", AlertMessage(
                    ex.message ?: "Não foi possível alterar sua senha. Entre em contato com o suporte.",
                    AlertMessageType.ERROR
                )
            )
        }

        return "redirect:/auth/login"
    }

    @GetMapping("/verify-account/{token}")
    fun verifyAccountView(
        @PathVariable("token", required = true) token: String,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            userService.activateAccount(token)
            redirectAttributes.addFlashAttribute(
                "alert",
                AlertMessage(
                    "Conta ativada com sucesso. Você já pode fazer o login.",
                    AlertMessageType.SUCCESS
                )
            )
        } catch (ex: Exception) {
            redirectAttributes.addFlashAttribute(
                "alert",
                AlertMessage(
                    ex.message!!,
                    AlertMessageType.ERROR
                )
            )
        }

        return "redirect:/auth/login"
    }

    @GetMapping("/locked-account")
    fun requestAccountVerifyView(
        @RequestParam("email", required = true) email: String,
        model: Model,
    ): String {
        model.addAttribute("email", email)
        return "auth/email-verify-view"
    }

    @PostMapping("/locked-account")
    fun requestAccountVerify(
        @RequestParam("email", required = true) email: String,
        redirectAttributes: RedirectAttributes
    ): String {

        userService.resendActivationAccountLinkToEmail(email)

        redirectAttributes.addFlashAttribute(
            "alert",
            AlertMessage(
                "Se o seu e-mail existir, você receberá o link de ativação.",
                AlertMessageType.INFO
            )
        )
        return "redirect:/auth/login"
    }

}