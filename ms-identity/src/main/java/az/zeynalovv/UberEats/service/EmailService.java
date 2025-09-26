package az.zeynalovv.UberEats.service;


import jakarta.mail.internet.MimeMessage;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;

  @Value("${application.base-url}")
  private String baseUrl;

  public void sendSimpleEmail(String to, String subject, String body) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(to);
    mailMessage.setSubject(subject);
    mailMessage.setText(body);
    mailSender.send(mailMessage);
  }

  @SneakyThrows
  public void sendHtmlEmail(String to, String subject, String body) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(body, true);
    mailSender.send(mimeMessage);
  }

  public void sendVerificationEmail(String email, String verificationLink) {
    Context context = new Context();
    context.setVariable("verificationBaseUrl", baseUrl);
    context.setVariable("verificationLink",
        URLEncoder.encode(verificationLink));

    String body = templateEngine.process("verification-email", context);
    sendHtmlEmail(email, "Email Verification", body);
  }

}
