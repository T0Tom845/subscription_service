package webrise.test.subscriptions_service.mapper;

import org.mapstruct.Mapper;
import webrise.test.subscriptions_service.dto.UserRequestDto;
import webrise.test.subscriptions_service.dto.UserResponseDto;
import webrise.test.subscriptions_service.model.User;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto dto);

    UserResponseDto toDto(User user);

    List<UserResponseDto> toDto(List<User> users);

    default String map(Instant instant) {
        return instant != null ? instant.toString() : null;
    }
}
