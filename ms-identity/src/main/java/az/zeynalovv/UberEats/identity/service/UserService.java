package az.zeynalovv.UberEats.identity.service;


import az.zeynalovv.UberEats.identity.dto.UserDto;
import az.zeynalovv.UberEats.identity.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.identity.entity.User;
import az.zeynalovv.UberEats.identity.exception.UserAlreadyExistsException;
import az.zeynalovv.UberEats.identity.exception.constant.ErrorMessage;
import az.zeynalovv.UberEats.identity.mapper.UserMapper;
import az.zeynalovv.UberEats.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

  private final RedisService redisService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final VerificationService verificationService;

  public void requestUserCreation(UserRegisterRequest user) {
    if (userRepository.existsUserByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())) {
      throw UserAlreadyExistsException.of(String.format(ErrorMessage.USER_ALREADY_EXISTS, user.getEmail()));
    }

    redisService.savePendingUser(user);
    verificationService.sendVerificationEmail(user.getEmail());
  }

  public UserDto createUser(String email) {
    User user = userMapper.toEntity(redisService.getPendingUser(email));
    userRepository.save(user);

    return userMapper.toDto(user);
  }

  public UserDto getUserByEmail(String email) {
    return userMapper.toDto(userRepository.findUserByEmail(email).orElse(null));
  }
}
