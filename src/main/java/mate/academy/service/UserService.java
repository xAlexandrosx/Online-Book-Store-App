package mate.academy.service;

import java.util.List;
import mate.academy.dto.user.UserRegistrationRequestDto;
import mate.academy.dto.user.UserResponseDto;

public interface UserService {

    UserResponseDto save(UserRegistrationRequestDto requestDto);

    List<UserResponseDto> findAll();

    UserResponseDto getById(Long id);

    UserResponseDto updateById(Long id, UserRegistrationRequestDto requestDto);

    void deleteById(Long id);
}
