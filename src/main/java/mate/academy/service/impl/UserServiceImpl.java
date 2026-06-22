package mate.academy.service.impl;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.user.UserRegistrationRequestDto;
import mate.academy.dto.user.UserResponseDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.exception.RegistrationException;
import mate.academy.mapper.UserMapper;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.repository.RoleRepository;
import mate.academy.repository.UserRepository;
import mate.academy.service.ShoppingCartService;
import mate.academy.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public UserResponseDto save(UserRegistrationRequestDto requestDto) {
        requestDto.setEmail(requestDto.getEmail().toLowerCase());
        if (userRepository.findByEmail(
                requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "User with email: "
                    + requestDto.getEmail()
                    + " already exists.");
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = userMapper.toEntity(requestDto);
        Role roleUser = roleRepository.findByName(Role.RoleName.USER).orElseThrow(
                () -> new EntityNotFoundException("Can't find role with type USER")
        );
        user.setRoles(Set.of(roleUser));
        User savedUser = userRepository.save(user);
        shoppingCartService.createShoppingCart(savedUser);
        return userMapper.toDto(savedUser);
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
                && userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
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
