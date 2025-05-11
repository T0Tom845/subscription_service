package webrise.test.subscriptions_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания подписки")
public record SubscriptionRequestDto(
        @NotBlank
        @Size(max = 255)
        @Schema(description = "Название подписки", example = "Spotify Premium")
        String name
) {
}
