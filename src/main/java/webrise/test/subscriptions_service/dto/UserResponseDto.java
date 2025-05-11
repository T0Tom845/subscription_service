package webrise.test.subscriptions_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO ответа с данными пользователя")
public record UserResponseDto(
        @Schema(description = "ID пользователя", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "Имя пользователя", example = "johndoe")
        String username,

        @Schema(description = "Email пользователя", example = "johndoe@example.com")
        String email,

        @Schema(description = "Дата создания", example = "2024-05-11T12:34:56Z")
        String createdAt

) {
}
