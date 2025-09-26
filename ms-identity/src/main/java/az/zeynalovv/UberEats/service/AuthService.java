package az.zeynalovv.UberEats.service;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import az.zeynalovv.UberEats.config.properties.ApplicationProperties;
import az.zeynalovv.UberEats.exception.TokenException;
import az.zeynalovv.UberEats.exception.constant.ErrorMessage;
import az.zeynalovv.UberEats.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final ApplicationProperties applicationProperties;
  private final UserService userService;

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");


  public Map<String, Object> verifyAccount(String token) {
    String[] parts = extractVerificationLink(token);

    if (parts == null) {
      throw new TokenException(ErrorMessage.INVALID_TOKEN);
    }

    String email = parts[0];
    String expiryDateString = parts[1];

    if (isVerificationTokenExpired(expiryDateString)) {
      throw new TokenException(ErrorMessage.TOKEN_HAS_EXPIRED);
    }

    return userService.createUser(email);
  }

  private String[] extractVerificationLink(String token) {
    String decryptedMessage = decryptMessage(token);
    String[] parts = decryptedMessage.split("/");

    if (parts.length != 2) {
      return null;
    }
    return parts;
  }

  private boolean isVerificationTokenExpired(String expiryDateString) {
    LocalDateTime expiryDate = LocalDateTime.parse(expiryDateString, formatter);
    return LocalDateTime.now().isAfter(expiryDate);
  }

  @SneakyThrows
  private String decryptMessage(String encryptedText) {
    PublicKey publicKey = CryptoUtil.loadPublicKey(
        applicationProperties.getSecurity().getAuthentication().getPublicKey());

    return new String(CryptoUtil.decryptText(publicKey, Base64.getDecoder().decode(encryptedText)));
  }
}
