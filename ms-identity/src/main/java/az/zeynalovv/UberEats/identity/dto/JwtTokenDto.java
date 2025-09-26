package az.zeynalovv.UberEats.identity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class JwtTokenDto {
  private String accessToken;
  private String refreshToken;
}
