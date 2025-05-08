package webrise.test.subscriptions_service.mapper;

import org.mapstruct.Mapper;
import webrise.test.subscriptions_service.dto.SubscriptionRequestDto;
import webrise.test.subscriptions_service.dto.UserSubscriptionResponseDto;
import webrise.test.subscriptions_service.model.Subscription;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    Subscription toEntity(SubscriptionRequestDto dto);
    UserSubscriptionResponseDto toDto(Subscription entity);

    Set<UserSubscriptionResponseDto> toDto(Set<Subscription> subscriptionsByUserId);
}
