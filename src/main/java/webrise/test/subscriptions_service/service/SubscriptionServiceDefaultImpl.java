package webrise.test.subscriptions_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import webrise.test.subscriptions_service.dto.SubscriptionRequestDto;
import webrise.test.subscriptions_service.dto.TopSubscriptionResponseDto;
import webrise.test.subscriptions_service.dto.UserSubscriptionResponseDto;
import webrise.test.subscriptions_service.exceptions.NoSuchSubscriptionException;
import webrise.test.subscriptions_service.exceptions.NoSuchUserException;
import webrise.test.subscriptions_service.mapper.SubscriptionMapper;
import webrise.test.subscriptions_service.model.Subscription;
import webrise.test.subscriptions_service.model.User;
import webrise.test.subscriptions_service.repository.SubscriptionRepository;
import webrise.test.subscriptions_service.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceDefaultImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<UserSubscriptionResponseDto> getSubscriptionsByUserId(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id %s not found", id)));
        return subscriptionMapper.toDto(user.getSubscriptions());
    }

    @Override
    @Transactional
    public UserSubscriptionResponseDto addSubscriptionToUser(UUID id, SubscriptionRequestDto dto) {
        Subscription subscription = subscriptionMapper.toEntity(dto);
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchUserException(String.format("User with id %s not found", id)));
        subscription.getUsers().add(user);
        return subscriptionMapper.toDto(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional
    public void deleteSubscriptionFromUser(UUID id, UUID subId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id %s not found", id)));
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new NoSuchSubscriptionException(String.format("Subscription with id %s not found", id)));
        if (user.getSubscriptions().remove(subscription)) {
            subscription.getUsers().remove(user);
            userRepository.save(user);
            subscriptionRepository.save(subscription);
        } else {
            throw new NoSuchSubscriptionException(String.format("Subscription with id %s not found on user with id %s", subId, id));
        }
    }
    @GetMapping("/subscriptions/top")
    public Set<TopSubscriptionResponseDto> getTopSubscriptions() {
        return subscriptionRepository.findTop3Subscriptions();
    }
}
