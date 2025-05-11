package webrise.test.subscriptions_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для отображения топовой подписки")
public record TopSubscriptionResponseDto(
        @Schema(description = "Название подписки", example = "Netflix")
        String name,
        @Schema(description = "Количество подписчиков", example = "123")
        Long subscribersNumber
) {
}
