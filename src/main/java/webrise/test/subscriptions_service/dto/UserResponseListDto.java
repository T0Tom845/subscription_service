package webrise.test.subscriptions_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO со списком пользователей")
public record UserResponseListDto (
        @Schema(description = "Список пользователей")
        List<UserResponseDto> userResponseDtos
){
}
