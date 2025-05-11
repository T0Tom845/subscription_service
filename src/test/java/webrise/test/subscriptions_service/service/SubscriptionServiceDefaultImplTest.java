package webrise.test.subscriptions_service.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import webrise.test.subscriptions_service.dto.SubscriptionRequestDto;
import webrise.test.subscriptions_service.dto.UserSubscriptionResponseDto;
import webrise.test.subscriptions_service.exceptions.NoSuchSubscriptionException;
import webrise.test.subscriptions_service.exceptions.NoSuchUserException;
import webrise.test.subscriptions_service.mapper.SubscriptionMapper;
import webrise.test.subscriptions_service.model.Subscription;
import webrise.test.subscriptions_service.model.User;
import webrise.test.subscriptions_service.repository.SubscriptionRepository;
import webrise.test.subscriptions_service.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceDefaultImplTest {
    @Mock private SubscriptionRepository subscriptionRepository;
    @Mock private UserRepository userRepository;
    @Mock private SubscriptionMapper subscriptionMapper;

    private final UUID userId = UUID.randomUUID();
    private final UUID subId = UUID.randomUUID();

    @InjectMocks
    private SubscriptionServiceDefaultImpl subscriptionService;

    @Test
    void getSubscriptionsByUserId_shouldReturnMappedSubscriptions() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setSubscriptions(Set.of(new Subscription()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(subscriptionMapper.toDto(Mockito.anySet())).thenReturn(Set.of(new UserSubscriptionResponseDto("Netflix", "2024-01-01")));

        Set<UserSubscriptionResponseDto> result = subscriptionService.getSubscriptionsByUserId(userId);
        Assertions.assertEquals(1, result.size());
    }
    @Test
    void getSubscriptionsByUserId_shouldThrowIfUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> subscriptionService.getSubscriptionsByUserId(userId));
    }

    @Test
    void addSubscriptionToUser_shouldSaveAndReturnDto() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Subscription sub = new Subscription();
        SubscriptionRequestDto dto = new SubscriptionRequestDto("HBO");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(subscriptionMapper.toEntity(dto)).thenReturn(sub);
        when(subscriptionRepository.save(sub)).thenReturn(sub);
        when(subscriptionMapper.toDto(sub)).thenReturn(new UserSubscriptionResponseDto("HBO", "2024-01-01"));

        UserSubscriptionResponseDto result = subscriptionService.addSubscriptionToUser(userId, dto);
        Assertions.assertEquals("HBO", result.name());
    }

    @Test
    void deleteSubscriptionFromUser_shouldThrowIfSubscriptionNotFoundOnUser() {
        User user = new User();
        Subscription sub = new Subscription();
        user.setSubscriptions(new HashSet<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(subscriptionRepository.findById(subId)).thenReturn(Optional.of(sub));

        assertThrows(NoSuchSubscriptionException.class, () -> subscriptionService.deleteSubscriptionFromUser(userId, subId));
    }

    @Test
    void deleteSubscriptionFromUser_shouldRemoveAndSave() {
        UUID userId = UUID.randomUUID();
        UUID subId = UUID.randomUUID();
        User user = new User();
        Subscription sub = new Subscription();

        user.setSubscriptions(new HashSet<>(Set.of(sub)));
        sub.setUsers(new HashSet<>(Set.of(user)));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(subscriptionRepository.findById(subId)).thenReturn(Optional.of(sub));

        subscriptionService.deleteSubscriptionFromUser(userId, subId);

        Mockito.verify(userRepository).save(user);
        Mockito.verify(subscriptionRepository).save(sub);
    }
}