package webrise.test.subscriptions_service.service;

import jakarta.validation.Valid;
import webrise.test.subscriptions_service.dto.UserRequestDto;
import webrise.test.subscriptions_service.dto.UserResponseDto;
import java.util.List;
import java.util.UUID;


public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto getById(UUID id);
    UserResponseDto create(UserRequestDto dto);
    UserResponseDto update(UUID id, UserRequestDto dto);

    void delete(UUID id);
}
