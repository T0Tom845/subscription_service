package webrise.test.subscriptions_service.service;

import webrise.test.subscriptions_service.dto.SubscriptionRequestDto;
import webrise.test.subscriptions_service.dto.TopSubscriptionResponseDto;
import webrise.test.subscriptions_service.dto.UserSubscriptionResponseDto;

import java.util.Set;
import java.util.UUID;

public interface SubscriptionService {
    Set<UserSubscriptionResponseDto> getSubscriptionsByUserId(UUID id);

    UserSubscriptionResponseDto addSubscriptionToUser(UUID id, SubscriptionRequestDto dto);

    void deleteSubscriptionFromUser(UUID id, UUID subId);

    Set<TopSubscriptionResponseDto> getTopSubscriptions();
}
