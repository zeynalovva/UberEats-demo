package az.zeynalovv.UberEats.controller;


import az.zeynalovv.UberEats.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.service.AuthService;
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

  @GetMapping("/verify")
  public ResponseEntity<Map<String, Object>> verifyAccount(@RequestParam("token") String token) {
    ;
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.verifyAccount(token));
  }


}
