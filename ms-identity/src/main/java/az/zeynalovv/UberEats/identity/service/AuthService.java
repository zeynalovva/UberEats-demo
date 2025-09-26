package az.zeynalovv.UberEats.identity.service;

import az.zeynalovv.UberEats.identity.dto.JwtTokenDto;
import az.zeynalovv.UberEats.identity.dto.UserDto;
import az.zeynalovv.UberEats.identity.dto.request.UserLoginRequest;
import az.zeynalovv.UberEats.identity.entity.User;
import az.zeynalovv.UberEats.identity.entity.enums.TokenType;
import az.zeynalovv.UberEats.identity.security.JwtService;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final EmailService emailService;
  private final VerificationService verificationService;
  private final JwtService jwtService;

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");


  public void loginRequest(UserLoginRequest userLoginRequest){
    verificationService.sendVerificationEmail(userLoginRequest.getEmail());
  }

  public JwtTokenDto login(UserDto user){
    return jwtService.generateToken(user);
  }

  public Object verifyLink(String verificationToken){
    String email = verificationService.verifyVerificationToken(verificationToken);
    UserDto user = userService.getUserByEmail(email);
    if (user != null) {
      return login(user);
    }

    return userService.createUser(email);
  }

}
