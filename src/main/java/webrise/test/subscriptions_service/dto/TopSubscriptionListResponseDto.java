package webrise.test.subscriptions_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;


@Schema(description = "DTO с топом популярных подписок")
public record TopSubscriptionListResponseDto(
        @Schema(description = "Топ популярных подписок")
        Set<TopSubscriptionResponseDto> topSubscriptionResponseDto
) {
}
