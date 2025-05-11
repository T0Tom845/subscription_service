package webrise.test.subscriptions_service.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import webrise.test.subscriptions_service.dto.UserRequestDto;
import webrise.test.subscriptions_service.dto.UserResponseDto;
import webrise.test.subscriptions_service.exceptions.NoSuchUserException;
import webrise.test.subscriptions_service.mapper.UserMapper;
import webrise.test.subscriptions_service.model.User;
import webrise.test.subscriptions_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceDefaultImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock private UserMapper userMapper;

    private final UUID userId = UUID.randomUUID();

    @InjectMocks
    private UserServiceDefaultImpl userService;

    @Test
    void getAllUsers_shouldReturnMappedList() {
        User user = new User();
        List<User> users = List.of(user);
        List<UserResponseDto> dtos = List.of(new UserResponseDto(UUID.randomUUID(), "user", "email", "2024-01-01"));

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(users)).thenReturn(dtos);

        List<UserResponseDto> result = userService.getAllUsers();
        Assertions.assertEquals(1, result.size());
    }
    @Test
    void create_shouldThrowWhenUsernameOrEmailNotUnique() {
        UserRequestDto dto = new UserRequestDto("duplicate", "duplicate@example.com");
        User user = new User();
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> userService.create(dto));
    }

    @Test
    void create_shouldMapAndSave() {
        UserRequestDto dto = new UserRequestDto("user", "email");
        User user = new User();
        User savedUser = new User();
        UserResponseDto responseDto = new UserResponseDto(UUID.randomUUID(), "user", "email", "2024-01-01");

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.create(dto);
        Assertions.assertEquals("user", result.username());
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getById(id));
    }

    @Test
    void update_shouldUpdateFields() {
        UUID id = UUID.randomUUID();
        User existing = new User();
        User updated = new User();
        UserRequestDto dto = new UserRequestDto("updated", "updated@email.com");
        UserResponseDto responseDto = new UserResponseDto(id, "updated", "updated@email.com", "2024-01-01");

        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(updated);
        when(userMapper.toDto(updated)).thenReturn(responseDto);

        UserResponseDto result = userService.update(id, dto);
        Assertions.assertEquals("updated", result.username());
    }
    @Test
    void update_shouldThrowIfUserNotFound() {
        UserRequestDto dto = new UserRequestDto("updated", "updated@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.update(userId, dto));
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        UUID id = UUID.randomUUID();
        userService.delete(id);
        Mockito.verify(userRepository).deleteById(id);
    }
}
