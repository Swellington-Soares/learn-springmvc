package dev.swell.myblog2.domain.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthUser : User {


    var user: PublicUser
        private set

    constructor(
        appUser: AppUser,
        username: String,
        password: String,
        authorities: Collection<GrantedAuthority?>?
    ) : super(
        username,
        password,
        authorities
    ) {
        this.user = UserMapper.INSTANCE.toPublicUser(appUser)
    }

    constructor(
        appUser: AppUser,
        username: String?,
        password: String?,
        enabled: Boolean,
        accountNonExpired: Boolean = true,
        credentialsNonExpired: Boolean = true,
        accountNonLocked: Boolean,
        authorities: Collection<GrantedAuthority?>?
    ) : super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities) {
        this.user = UserMapper.INSTANCE.toPublicUser(appUser)
    }

    companion object {
        fun builder(): UserBuilder = User.builder()
    }

}



