package mate.academy.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.user.UserRegistrationRequestDto;
import mate.academy.dto.user.UserResponseDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.exception.RegistrationException;
import mate.academy.mapper.UserMapper;
import mate.academy.model.User;
import mate.academy.repository.UserRepository;
import mate.academy.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto requestDto) {
        if (!userRepository.findByEmailIgnoreCase(
                requestDto.getEmail()).isEmpty()) {
            throw new RegistrationException(
                    "User with email: "
                    + requestDto.getEmail()
                    + " already exists.");
        }
        User saved = userRepository.save(userMapper.toEntity(requestDto));
        return userMapper.toDto(saved);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find user with id: " + id)
        );
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateById(Long id, UserRegistrationRequestDto requestDto) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot update. User not found with id: " + id)
        );

        if (!existingUser.getEmail().equalsIgnoreCase(requestDto.getEmail())
                && !userRepository.findByEmailIgnoreCase(requestDto.getEmail()).isEmpty()) {
            throw new RegistrationException(
                    "Email " + requestDto.getEmail() + " is already in use.");
        }

        userMapper.updateUserFromDto(requestDto, existingUser);

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find user with id: " + id);
        }

        userRepository.deleteById(id);

    }
}
