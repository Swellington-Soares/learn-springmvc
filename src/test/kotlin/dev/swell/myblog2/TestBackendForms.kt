package dev.swell.myblog2

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


fun Map<String, String>.toMultiValueMap(): MultiValueMap<String, String> {
    val multiValueMap = LinkedMultiValueMap<String, String>()
    this.forEach { (key, value) -> multiValueMap.add(key, value) }
    return multiValueMap
}


@SpringBootTest
@AutoConfigureMockMvc
class TestBackendForms(
    @Autowired val mockMvc: MockMvc,
) {

    @Test
    fun `test if user register is saved successfully`() {
        val formData = mapOf(
            "firstname" to "John",
            "lastname" to "Doe",
            "email" to "john.doe@dev.net",
            "username" to "johndoe",
            "password" to "12345678"
        )
        mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .formFields(formData.toMultiValueMap())
        ).andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/auth/login"))
    }

}