package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.user.UserRegistrationRequestDto;
import mate.academy.dto.user.UserResponseDto;
import mate.academy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toEntity(UserRegistrationRequestDto requestDto);

    UserResponseDto toDto(User user);

    void updateUserFromDto(UserRegistrationRequestDto dto, @MappingTarget User user);
}
