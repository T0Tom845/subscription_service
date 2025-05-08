package webrise.test.subscriptions_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webrise.test.subscriptions_service.dto.TopSubscriptionResponseDto;
import webrise.test.subscriptions_service.dto.UserSubscriptionResponseDto;
import webrise.test.subscriptions_service.model.Subscription;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {


    @Query(
            value = """
            SELECT s.name AS name, COUNT(us.user_id) AS subscribersNumber
            FROM subscription_scheme.subscriptions s
            LEFT JOIN subscription_scheme.user_subscriptions us
                ON s.id = us.subscription_id
            GROUP BY s.id
            ORDER BY subscribersNumber DESC
            LIMIT 3
            """,
            nativeQuery = true
    )
    Set<TopSubscriptionResponseDto> findTop3Subscriptions();
}
