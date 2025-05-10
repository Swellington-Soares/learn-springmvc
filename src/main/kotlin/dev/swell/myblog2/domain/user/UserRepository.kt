package dev.swell.myblog2.domain.user

import dev.swell.myblog2.architecture.validation.UniqueUsername
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<AppUser, Long> {
    fun findByEmail(email: String): Optional<AppUser>
    fun existsByEmail(email: String?): Boolean
    fun existsByUsername(username: String?): Boolean
}