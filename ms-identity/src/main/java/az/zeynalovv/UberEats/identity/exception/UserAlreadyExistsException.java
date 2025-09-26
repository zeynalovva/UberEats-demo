package az.zeynalovv.UberEats.identity.exception;

public class UserAlreadyExistsException extends RuntimeException {

  private UserAlreadyExistsException(String message) {
    super(message);
  }

  public static UserAlreadyExistsException of(String message) {
    return new UserAlreadyExistsException(message);
  }
}
