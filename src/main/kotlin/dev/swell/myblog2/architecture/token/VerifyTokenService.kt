package dev.swell.myblog2.architecture.token

import dev.swell.myblog2.domain.user.AppUser
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@Service
class VerifyTokenService(
    private val verifyTokenRepository: VerifyTokenRepository
) {
    private fun createNewToken(user: AppUser, expireIn: LocalDateTime, type: VerifyTokenType): VerifyToken {
        val token = VerifyToken(
            token = VerifyToken.generateTokenString(),
            expireIn = expireIn,
            user = user,
            tokenType = type
        )
        if ( verifyTokenRepository.findVerifyTokenByUserAndTokenType(user, type).isPresent ){
            verifyTokenRepository.deleteVerifyTokenByUserAndTokenType(user, type)
        }
        return verifyTokenRepository.save(token)
    }

    fun createEmailToken(user: AppUser, expireIn: LocalDateTime): VerifyToken {
        return createNewToken(user, expireIn, VerifyTokenType.ACTIVE_ACCOUNT_TOKEN)
    }

    fun createRecoverPasswordToken(user: AppUser, expireIn: LocalDateTime): VerifyToken {
        return createNewToken(user, expireIn, VerifyTokenType.RECOVER_PASSWORD_TOKEN)
    }

    fun findTokenInfoByTokenIdentify( token: String ): Optional<VerifyToken> {
        return verifyTokenRepository.findVerifyTokenByToken(token)
    }

    fun findUseTokenByType( user: AppUser, type: VerifyTokenType ): Optional<VerifyToken> {
        return verifyTokenRepository.findVerifyTokenByUserAndTokenType( user, type )
    }

    fun updateTokenExpireIn( token: String, expireIn: LocalDateTime) : Boolean {
        return findTokenInfoByTokenIdentify(token).map {
            it.expireIn = expireIn
            verifyTokenRepository.save(it)
            true
        }.orElse(false)
    }

    fun deleteToken(token: String) {
        verifyTokenRepository.deleteByToken(token)
    }

    fun deleteToken(token: VerifyToken) {
        verifyTokenRepository.delete(token)
    }

    fun isTokenValid(token: String): Boolean {
        val verifyToken = verifyTokenRepository.findVerifyTokenByToken(token).getOrNull()
        if (verifyToken == null) return false
        return !verifyToken.isExpired()
    }


}