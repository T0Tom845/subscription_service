package webrise.test.subscriptions_service.exceptions;

public class NoSuchUserException extends RuntimeException {
  public NoSuchUserException(String message) {
    super(message);
  }
}
