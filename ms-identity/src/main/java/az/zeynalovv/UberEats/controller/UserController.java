package az.zeynalovv.UberEats.controller;


import az.zeynalovv.UberEats.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<String> createUser(@RequestBody UserRegisterRequest userRegisterRequest) {
    userService.requestUserCreation(userRegisterRequest);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("User creation requested successfully");
  }

}
