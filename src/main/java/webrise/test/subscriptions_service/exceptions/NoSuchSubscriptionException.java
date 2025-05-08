package webrise.test.subscriptions_service.exceptions;

public class NoSuchSubscriptionException extends RuntimeException {
    public NoSuchSubscriptionException(String message) {
        super(message);
    }
}
