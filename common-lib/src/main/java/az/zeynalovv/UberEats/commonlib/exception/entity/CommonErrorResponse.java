package az.zeynalovv.UberEats.commonlib.exception.entity;


import lombok.Data;

@Data
public class CommonErrorResponse {
  private String errorCode;
  private String message;
}
