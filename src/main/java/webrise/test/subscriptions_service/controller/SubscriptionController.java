package webrise.test.subscriptions_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webrise.test.subscriptions_service.dto.SubscriptionRequestDto;
import webrise.test.subscriptions_service.dto.TopSubscriptionResponseDto;
import webrise.test.subscriptions_service.dto.UserSubscriptionResponseDto;
import webrise.test.subscriptions_service.service.SubscriptionService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("users/{id}/subscriptions")
    public Set<UserSubscriptionResponseDto> getUserSubscriptions(@PathVariable UUID id) {
        return subscriptionService.getSubscriptionsByUserId(id);
    }
    @PostMapping("users/{id}/subscriptions")
    public UserSubscriptionResponseDto addSubscriptionToUser(@PathVariable UUID id, @RequestBody @Valid SubscriptionRequestDto dto){
        return subscriptionService.addSubscriptionToUser(id, dto);
    }
    @DeleteMapping("users/{id}/subscriptions/{sub_id}")
    public void deleteSubscription(@PathVariable UUID id, @PathVariable(name = "sub_id") UUID sub_id){
        subscriptionService.deleteSubscriptionFromUser(id, sub_id);
    }
    @GetMapping("/subscriptions/top")
    public Set<TopSubscriptionResponseDto> getTopSubscriptions() {
        return subscriptionService.getTopSubscriptions();
    }
}
