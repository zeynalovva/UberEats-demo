package az.zeynalovv.UberEats.identity.exception;

public class InvalidVerificationTokenException extends RuntimeException{
  private InvalidVerificationTokenException(String message) {
    super(message);
  }

  public static InvalidVerificationTokenException of(String message) {
    return new InvalidVerificationTokenException(message);
  }
}
