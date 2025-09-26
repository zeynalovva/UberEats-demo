package az.zeynalovv.UberEats.identity.service;


import az.zeynalovv.UberEats.identity.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.identity.exception.PendingUserNotFoundException;
import az.zeynalovv.UberEats.identity.exception.constant.ErrorMessage;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, UserRegisterRequest> redisTemplate;
  private static final long VERIFICATION_TOKEN_TTL_MIN = 30;


  public void savePendingUser(UserRegisterRequest user) {
    String key = "user::" + user.getEmail();
    redisTemplate.opsForValue().set(key, user, VERIFICATION_TOKEN_TTL_MIN, TimeUnit.MINUTES);
  }

  public UserRegisterRequest getPendingUser(String email) {
    String key = "user::" + email;
    UserRegisterRequest user = redisTemplate.opsForValue().get(key);

    if (user == null) {
      throw PendingUserNotFoundException.of(ErrorMessage.NO_PENDING_USER_FOUND + email);
    }

    redisTemplate.delete(key);

    return user;
  }


}
