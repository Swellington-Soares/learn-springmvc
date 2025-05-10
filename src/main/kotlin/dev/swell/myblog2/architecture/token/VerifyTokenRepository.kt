package dev.swell.myblog2.architecture.token

import dev.swell.myblog2.domain.user.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface VerifyTokenRepository: JpaRepository<VerifyToken, Long> {
    fun findVerifyTokenByToken(token: String): Optional<VerifyToken>
    fun findAllByUser(user: AppUser): List<VerifyToken>
    fun deleteByToken(token: String)
    fun findVerifyTokenByUserAndTokenType(user: AppUser, type: VerifyTokenType): Optional<VerifyToken>
    fun deleteVerifyTokenByUserAndTokenType(user: AppUser, type: VerifyTokenType)
}