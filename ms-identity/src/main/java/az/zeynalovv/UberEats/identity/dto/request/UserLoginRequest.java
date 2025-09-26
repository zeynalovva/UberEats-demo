package az.zeynalovv.UberEats.identity.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
  private String email;
}
