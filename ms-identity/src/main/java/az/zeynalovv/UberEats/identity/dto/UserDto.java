package az.zeynalovv.UberEats.identity.dto;

import az.zeynalovv.UberEats.identity.entity.enums.UserRole;
import az.zeynalovv.UberEats.identity.entity.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class UserDto {

  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private UserRole userRole;
  private UserStatus userStatus;
}
