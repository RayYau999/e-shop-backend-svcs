package com.rayyau.eshop.security.library.Mapper;

import com.rayyau.eshop.security.library.dto.UserDetailsWithoutPasswordDto;
import com.rayyau.eshop.security.library.dto.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDetailsWithoutPasswordDto userEntityToUserDetailsDto(UserEntity userEntity);
}
