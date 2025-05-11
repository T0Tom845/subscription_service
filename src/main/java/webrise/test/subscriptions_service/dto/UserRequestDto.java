package webrise.test.subscriptions_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO запроса на создание пользователя")
public record UserRequestDto(
        @NotBlank
        @Size(max = 255)
        @Schema(description = "Имя пользователя", example = "johndoe")
        String username,
        @NotBlank
        @Size(max = 255)
        @Schema(description = "Email пользователя", example = "johndoe@example.com")
        String email
) {
}
