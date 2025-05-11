package webrise.test.subscriptions_service.dto;

import java.util.Set;

public record TopSubscriptionListResponseDto(
        Set<TopSubscriptionResponseDto> topSubscriptionResponseDto
) {
}
