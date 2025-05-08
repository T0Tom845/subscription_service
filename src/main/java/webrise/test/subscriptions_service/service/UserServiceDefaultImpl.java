package webrise.test.subscriptions_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webrise.test.subscriptions_service.dto.UserRequestDto;
import webrise.test.subscriptions_service.dto.UserResponseDto;
import webrise.test.subscriptions_service.exceptions.NoSuchUserException;
import webrise.test.subscriptions_service.mapper.UserMapper;
import webrise.test.subscriptions_service.model.User;
import webrise.test.subscriptions_service.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceDefaultImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id %s not found", id)));
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        try {
            User user = userMapper.toEntity(dto);
            return userMapper.toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponseDto update(UUID id, UserRequestDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id %s not found", id)));
        existingUser.setEmail(dto.email());
        existingUser.setUsername(dto.username());
        return userMapper.toDto(userRepository.save(existingUser));

    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
