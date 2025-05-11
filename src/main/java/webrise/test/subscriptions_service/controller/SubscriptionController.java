package webrise.test.subscriptions_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webrise.test.subscriptions_service.dto.*;
import webrise.test.subscriptions_service.service.SubscriptionService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("users/{id}/subscriptions")
    public UserSubscriptionSetResponseDto getUserSubscriptions(@PathVariable UUID id) {
        Set<UserSubscriptionResponseDto> userSubscriptionsDto = subscriptionService.getSubscriptionsByUserId(id);
        return new UserSubscriptionSetResponseDto(userSubscriptionsDto);
    }

    @PostMapping("users/{id}/subscriptions")
    public UserSubscriptionResponseDto addSubscriptionToUser(@PathVariable UUID id, @RequestBody @Valid SubscriptionRequestDto dto) {
        return subscriptionService.addSubscriptionToUser(id, dto);
    }

    @DeleteMapping("users/{id}/subscriptions/{subId}")
    public void deleteSubscription(@PathVariable UUID id, @PathVariable(name = "subId") UUID subId) {
        subscriptionService.deleteSubscriptionFromUser(id, subId);
    }

    @GetMapping("/subscriptions/top")
    public TopSubscriptionListResponseDto getTopSubscriptions() {
        Set <TopSubscriptionResponseDto> topSubscriptionResponseDtos = subscriptionService.getTopSubscriptions();
        return new TopSubscriptionListResponseDto(topSubscriptionResponseDtos);
    }
}
