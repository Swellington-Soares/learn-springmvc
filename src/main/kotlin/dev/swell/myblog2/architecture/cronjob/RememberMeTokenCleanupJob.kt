package dev.swell.myblog2.architecture.cronjob

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import javax.sql.DataSource


@Component
class RememberMeTokenCleanupJob(
    private val dataSource: DataSource
) {

    @Value("\${spring.security.rememberme.token-max-time-in-seconds}")
    private var remeberTokenExpireTime: Long = 604800

    @Scheduled(cron = "0 0 3 * * *") // all days
    fun cleanExpiredTokens() {
        val expirationThreshold = Timestamp.from(Instant.now().minusSeconds(remeberTokenExpireTime))
        dataSource.connection.use { conn ->
            conn.prepareStatement("DELETE FROM `persistent_logins` WHERE last_used < ?").use { stmt ->
                stmt.setTimestamp(1, expirationThreshold)
                val deleted = stmt.executeUpdate()
                println("Limpou $deleted tokens remember-me expirados.")
            }
        }
    }

}