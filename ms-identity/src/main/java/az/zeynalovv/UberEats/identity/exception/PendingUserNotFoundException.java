package az.zeynalovv.UberEats.identity.exception;

public class PendingUserNotFoundException extends RuntimeException {

  private PendingUserNotFoundException(String message) {
    super(message);
  }

  public static PendingUserNotFoundException of(String message) {
    return new PendingUserNotFoundException(message);
  }
}
