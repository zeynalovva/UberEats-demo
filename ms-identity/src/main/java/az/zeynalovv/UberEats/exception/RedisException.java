package az.zeynalovv.UberEats.exception;

public class RedisException extends RuntimeException {
    public RedisException(String message) {
        super(message);
    }
}
