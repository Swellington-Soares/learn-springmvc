package dev.swell.myblog2.architecture.token

import dev.swell.myblog2.domain.user.AppUser
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.Constraint
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(
    name = "verify_tokens",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["token", "user_id", "token_type" ])
    ]
)
data class VerifyToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Column(nullable = false, updatable = false, unique = true)
    val token: String,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: AppUser,

    @Column(nullable = false)
    var expireIn: LocalDateTime,

    @Enumerated(EnumType.STRING)
    val tokenType: VerifyTokenType

) {

    fun isExpired() = LocalDateTime.now() > expireIn

    fun isActiveAccountType() = tokenType == VerifyTokenType.ACTIVE_ACCOUNT_TOKEN
    fun isRecoverPasswordType() = tokenType == VerifyTokenType.RECOVER_PASSWORD_TOKEN


    companion object Factory {
        fun generateTokenString() = UUID.randomUUID().toString()
    }

}

enum class VerifyTokenType {
    ACTIVE_ACCOUNT_TOKEN,
    RECOVER_PASSWORD_TOKEN
}
