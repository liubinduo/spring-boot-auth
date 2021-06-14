package com.v1ok.commons.error;

import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.impl.RestResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public IRestResponse<?> exceptionHandler(Exception exception){
    log.error("服务器运行时出错未知错误",exception);
    return RestResponse.builder().error(HeadCode.ERROR).message(exception.getMessage());
  }



  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthorityException.class)
  @ResponseBody
  public IRestResponse<?> authorityExceptionHandler(AuthorityException exception) {
    log.error("需要登录校验没通过。", exception);
    return RestResponse.builder().error(HeadCode.UN_AUTHORIZED);
  }

  /**
   * Customize the response for MethodArgumentNotValidException.
   * <p>This method delegates to {@link #handleExceptionInternal}.
   *
   * @param exception the exception
   * @param headers the headers to be written to the response
   * @param status the selected response status
   * @param request the current request
   * @return a {@code ResponseEntity} instance
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    BindingResult exceptions = exception.getBindingResult();
    // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
    if (exceptions.hasErrors()) {
      List<ObjectError> errors = exceptions.getAllErrors();
      if (!errors.isEmpty()) {
        // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
        FieldError fieldError = (FieldError) errors.get(0);
        return ResponseEntity.badRequest()
            .body(fieldError.getField() + ":" + fieldError.getDefaultMessage());
      }
    }
    return ResponseEntity.badRequest().body(exception.getMessage());
  }
}
