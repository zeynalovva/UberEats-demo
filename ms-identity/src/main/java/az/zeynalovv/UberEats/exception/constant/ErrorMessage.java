package az.zeynalovv.UberEats.exception.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorMessage {
    public final static String TOKEN_HAS_EXPIRED = "Token has expired";
    public final static String INVALID_TOKEN = "Invalid token format";
    public final static String NO_PENDING_USER_FOUND = "No pending user found for email: ";
    public final static String USER_ALREADY_EXISTS = "User with email %s already exists";
}
