package dev.swell.myblog2.controller

import dev.swell.myblog2.domain.user.UserService
import dev.swell.myblog2.dto.request.RegisterUserDTO
import dev.swell.myblog2.dto.response.AlertMessage
import dev.swell.myblog2.dto.response.AlertMessageType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class UserController(
    private val userService: UserService
) {

    @GetMapping("/register")
    fun registerView(model: Model): String {
        val registerDTO = RegisterUserDTO.Factory.default()
        model.addAttribute("userform", registerDTO)
        return "auth/register-view"
    }

    @PostMapping("/register")
    fun registerUser(
        @ModelAttribute("userform") @Validated dto: RegisterUserDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userform", dto)
            val errors = bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
            model.addAttribute("errors", errors)
            return "auth/register-view"
        }

        userService.register( dto )

        redirectAttributes.addFlashAttribute("alert",AlertMessage("Conta criada com sucesso.", AlertMessageType.SUCCESS))
        return "redirect:/auth/login"

    }
}