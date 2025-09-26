package az.zeynalovv.UberEats.identity.exception;

import az.zeynalovv.UberEats.commonlib.exception.entity.CommonErrorResponse;
import az.zeynalovv.UberEats.identity.exception.constant.ErrorCode;
import az.zeynalovv.UberEats.identity.exception.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

  @ExceptionHandler(PendingUserNotFoundException.class)
  public ResponseEntity<CommonErrorResponse> handlePendingUserNotFoundException(PendingUserNotFoundException ex) {
    CommonErrorResponse errorResponse = new CommonErrorResponse();
    errorResponse.setErrorCode(ErrorCode.DATA_NOT_FOUND);
    errorResponse.setMessage(ex.getMessage());
    return ResponseEntity.status(404).body(errorResponse);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<CommonErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    CommonErrorResponse errorResponse = new CommonErrorResponse();
    errorResponse.setErrorCode(ErrorCode.ALREADY_EXIST);
    errorResponse.setMessage(ex.getMessage());
    return ResponseEntity.status(409).body(errorResponse);
  }

  @ExceptionHandler(InvalidVerificationTokenException.class)
  public ResponseEntity<CommonErrorResponse> handleInvalidVerificationTokenException(
      InvalidVerificationTokenException ex) {
    CommonErrorResponse errorResponse = new CommonErrorResponse();
    errorResponse.setErrorCode(ErrorCode.INVALID_TOKEN);
    errorResponse.setMessage(ex.getMessage());
    return ResponseEntity.status(400).body(errorResponse);
  }

  @ExceptionHandler(ExpiredVerificationTokenException.class)
  public ResponseEntity<CommonErrorResponse> handleExpiredVerificationTokenException(
      ExpiredVerificationTokenException ex) {
    CommonErrorResponse errorResponse = new CommonErrorResponse();
    errorResponse.setErrorCode(ErrorCode.INVALID_TOKEN);
    errorResponse.setMessage(ex.getMessage());
    return ResponseEntity.status(400).body(errorResponse);
  }

  //@ExceptionHandler(Exception.class)
  //public ResponseEntity<CommonErrorResponse> handleGenericException(Exception ex, WebRequest request) {
  //  CommonErrorResponse errorResponse = new CommonErrorResponse();
  //  errorResponse.setErrorCode(ErrorCode.UNEXPECTED_INTERNAL_ERROR);
  //  errorResponse.setMessage(ErrorMessage.INTERNAL_SERVER_ERROR + ex.getMessage() + request.getContextPath());
  //  return ResponseEntity.status(500).body(errorResponse);
  //}



}
