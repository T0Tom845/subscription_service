package webrise.test.subscriptions_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank
        @Size(max = 255)
        String username,
        @NotBlank
        @Size(max = 255)
        String email
) {
}
