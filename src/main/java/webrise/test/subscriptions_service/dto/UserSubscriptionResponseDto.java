package webrise.test.subscriptions_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO одной подписки пользователя")
public record UserSubscriptionResponseDto(
        @Schema(description = "Название подписки", example = "YouTube Premium")
        String name,

        @Schema(description = "Дата подписки", example = "2024-05-10T15:00:00Z")
        String createdAt
) {
}
