package webrise.test.subscriptions_service.dto;

import java.util.Set;

public record UserSubscriptionListResponseDto(
        Set<UserSubscriptionResponseDto> subscriptions
) {
}
