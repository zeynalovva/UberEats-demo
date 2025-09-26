package az.zeynalovv.UberEats.identity.service;


import az.zeynalovv.UberEats.identity.config.properties.ApplicationProperties;
import az.zeynalovv.UberEats.identity.exception.ExpiredVerificationTokenException;
import az.zeynalovv.UberEats.identity.exception.InvalidVerificationTokenException;
import az.zeynalovv.UberEats.identity.exception.constant.ErrorMessage;
import az.zeynalovv.UberEats.identity.util.CryptoUtil;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class VerificationService {
  private final ApplicationProperties applicationProperties;
  private final EmailService emailService;
  private final SpringTemplateEngine templateEngine;

  @Value("${application.base-url}")
  private String baseUrl;

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");

  public String verifyVerificationToken(String token) {
    String[] parts = extractVerificationToken(token);

    if (parts == null) {
      throw InvalidVerificationTokenException.of(ErrorMessage.INVALID_TOKEN);
    }

    String email = parts[0];
    String expiryDateString = parts[1];

    if (isVerificationTokenExpired(expiryDateString)) {
      throw ExpiredVerificationTokenException.of(ErrorMessage.TOKEN_HAS_EXPIRED);
    }
    return email;
  }

  private String[] extractVerificationToken(String token) {
    String decryptedMessage = decryptMessage(token);
    String[] parts = decryptedMessage.split("/");

    if (parts.length != 2) {
      return null;
    }
    return parts;
  }

  public void sendVerificationEmail(String email) {
    String verificationToken = generateVerificationToken(email);
    System.out.println(verificationToken);
    Context context = new Context();
    context.setVariable("verificationBaseUrl", baseUrl);
    context.setVariable("verificationLink",
        URLEncoder.encode(verificationToken));

    String body = templateEngine.process("verification-email", context);
    emailService.sendHtmlEmail(email, "Email Verification", body);
  }

  private String generateVerificationToken(String email) {
    LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String expiryDateString = expiryDate.format(formatter);
    String subject = email + "/" + expiryDateString;

    return encryptMessage(subject);
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

  @SneakyThrows
  private String encryptMessage(String plainText) {
    PrivateKey privateKey = CryptoUtil.loadPrivateKey(applicationProperties.getSecurity()
        .getAuthentication().getPrivateKey());

    byte[] encryptedText = CryptoUtil.encryptText(privateKey, plainText.getBytes());
    return Base64.getEncoder().encodeToString(encryptedText);
  }
}
