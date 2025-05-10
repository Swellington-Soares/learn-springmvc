package dev.swell.myblog2.domain.user

import dev.swell.myblog2.dto.request.RegisterUserDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring")
interface UserMapper {

    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    fun toEntity(user: RegisterUserDTO): AppUser
    fun fromEntityToDTO(user: AppUser): RegisterUserDTO
    fun toPublicUser(user: AppUser): PublicUser
}