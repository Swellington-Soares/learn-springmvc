package dev.swell.myblog2.architecture.exception

class UserAccountNotVerified: RuntimeException("User account not verified.")
class UserAccountBanned: RuntimeException("User account banned.")
class VerifyTokenNotFoundException: RuntimeException("Token not found.")
class VerifyTokenHasExpiredException: RuntimeException("Token has expired.")
class VerifyTokenInvalidTypeException: RuntimeException("Token has invalid type.")