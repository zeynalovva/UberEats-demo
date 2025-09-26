package az.zeynalovv.UberEats.identity.service;


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

  @SneakyThrows
  public void sendHtmlEmail(String to, String subject, String body) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(body, true);
    mailSender.send(mimeMessage);
  }

}
