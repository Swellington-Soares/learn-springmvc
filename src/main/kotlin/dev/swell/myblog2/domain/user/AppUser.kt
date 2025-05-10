package dev.swell.myblog2.domain.user

import dev.swell.myblog2.architecture.token.VerifyToken
import jakarta.annotation.Nullable
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity(name = "User")
@Table(name = "users")
data class AppUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, updatable = false, unique = true)
    val email: String,

    @Column(nullable = false, updatable = false, unique = true, columnDefinition = "varchar(64)")
    val username: String,

    @Column(nullable = false, updatable = true)
    var password: String,

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    var avatar: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var firstname: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var lastname: String,

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    var emailVerified: Boolean = false,

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    var disabled: Boolean = false,

    ) {

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP()")
    val createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val tokens: MutableList<VerifyToken> = mutableListOf()


}