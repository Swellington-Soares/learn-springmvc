package dev.swell.myblog2.dto.response

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AlertMessage @OptIn(ExperimentalUuidApi::class) constructor(
    val message: String,
    val type: AlertMessageType = AlertMessageType.DEFAULT,
    val id: String = "alert-${Uuid.random().toHexString()}"
)

enum class AlertMessageType {
    DEFAULT,
    INFO,
    WARNING,
    ERROR,
    SUCCESS,
}