package az.zeynalovv.UberEats.identity.controller;


import az.zeynalovv.UberEats.identity.dto.UserDto;
import az.zeynalovv.UberEats.identity.dto.request.UserLoginRequest;
import az.zeynalovv.UberEats.identity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/magic-link/verify")
  public ResponseEntity<Object> magicLink(@RequestParam("token") String token) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.verifyLink(token));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(String email) {
    authService.loginRequest(UserLoginRequest
        .builder()
        .email(email)
        .build());
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login verification email sent successfully");
  }


}
