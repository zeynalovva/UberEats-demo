package az.zeynalovv.UberEats.service;


import az.zeynalovv.UberEats.config.properties.ApplicationProperties;
import az.zeynalovv.UberEats.dto.UserDto;
import az.zeynalovv.UberEats.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.entity.User;
import az.zeynalovv.UberEats.entity.enums.TokenType;
import az.zeynalovv.UberEats.exception.UserException;
import az.zeynalovv.UberEats.exception.constant.ErrorMessage;
import az.zeynalovv.UberEats.mapper.UserMapper;
import az.zeynalovv.UberEats.repository.UserRepository;
import az.zeynalovv.UberEats.security.JwtService;
import az.zeynalovv.UberEats.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

  private final ApplicationProperties applicationProperties;
  private final EmailService emailService;
  private final JwtService jwtService;
  private final RedisService redisService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final RedisTemplate<String, UserRegisterRequest> redisTemplate;

  public void requestUserCreation(UserRegisterRequest user) {
    if (userRepository.existsUserByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())) {
      throw new UserException(String.format(ErrorMessage.USER_ALREADY_EXISTS, user.getEmail()));
    }

    redisService.savePendingUser(user);
    emailService.sendVerificationEmail(user.getEmail(), generateVerificationLink(user.getEmail()));
  }

  public Map<String, Object> createUser(String email) {
    User user = userMapper.toEntity(redisService.getPendingUser(email));
    userRepository.save(user);

    UserDto userDto = userMapper.toDto(user);
    String jwtToken = jwtService.generateToken(userDto, TokenType.ACCESS);

    return generateSuccessfulResponse(jwtToken, userDto);
  }

  private Map<String, Object> generateSuccessfulResponse(String token, UserDto user) {
    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("user", user);
    return response;
  }

  private String generateVerificationLink(String email) {
    LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String expiryDateString = expiryDate.format(formatter);
    String subject = email + "/" + expiryDateString;

    return encryptMessage(subject);
  }

  @SneakyThrows
  private String encryptMessage(String plainText) {
    PrivateKey privateKey = CryptoUtil.loadPrivateKey(applicationProperties.getSecurity()
        .getAuthentication().getPrivateKey());

    byte[] encryptedText = CryptoUtil.encryptText(privateKey, plainText.getBytes());
    return Base64.getEncoder().encodeToString(encryptedText);
  }
}
