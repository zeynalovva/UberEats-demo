package az.zeynalovv.UberEats.identity.exception.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorMessage {

  public static final String TOKEN_HAS_EXPIRED = "Token has expired";
  public static final String INVALID_TOKEN = "Invalid token format";
  public static final String NO_PENDING_USER_FOUND = "No pending user found for email: ";
  public static final String USER_ALREADY_EXISTS = "User with email %s already exists";
  public static final String INTERNAL_SERVER_ERROR = "Internal server error: ";
}
