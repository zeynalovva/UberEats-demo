package az.zeynalovv.UberEats.identity.exception;

public class ExpiredVerificationTokenException extends RuntimeException {

  private ExpiredVerificationTokenException(String message) {
    super(message);
  }

  public static ExpiredVerificationTokenException of(String message) {
    return new ExpiredVerificationTokenException(message);
  }
}
