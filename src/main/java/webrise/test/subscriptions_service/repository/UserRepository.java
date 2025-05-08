package webrise.test.subscriptions_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webrise.test.subscriptions_service.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
