package dev.swell.myblog2.domain.user

import gg.jte.Content
import gg.jte.TemplateOutput

data class PublicUser(
    val id: Long?,
    val email: String,
    val username: String,
    val firstname: String,
    val lastname: String,
    val avatar: String?
) : Content {

    fun getFullName() = "$firstname $lastname"

    override fun writeTo(output: TemplateOutput?) {
        val str = this.toString()
        output?.writeContent(str)
    }

}
