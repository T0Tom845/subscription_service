package webrise.test.subscriptions_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "DTO со списком подписок пользователя")
public record UserSubscriptionSetResponseDto(
        @Schema(description = "Подписки пользователя")
        Set<UserSubscriptionResponseDto> subscriptions
) {
}
