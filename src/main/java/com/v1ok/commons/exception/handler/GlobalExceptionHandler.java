package com.v1ok.commons.exception.handler;

import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.exception.OperationException;
import com.v1ok.commons.impl.RestResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
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
import org.springframework.web.util.WebUtils;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public IRestResponse<?> exceptionHandler(Exception exception) {
    log.error("服务器运行时出错未知错误", exception);
    return RestResponse.builder().error(HeadCode.ERROR).message(exception.getMessage());
  }

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(OperationException.class)
  @ResponseBody
  public IRestResponse<?> exceptionHandler(OperationException exception) {
    log.error("服务器运行时出错", exception);
    return RestResponse.builder().error(HeadCode.BAD_REQUEST).message(exception.getMessage());
  }


  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthorityException.class)
  @ResponseBody
  public IRestResponse<?> authorityExceptionHandler(AuthorityException exception) {
    log.error("需要登录校验没通过。", exception);
    return RestResponse.builder().error(HeadCode.UN_AUTHORIZED);
  }

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("服务器运行时出错", ex);

    ResponseEntity<Object> responseEntity = super.handleMethodArgumentNotValid(ex, headers, status,
        request);

    BindingResult bindingResult = ex.getBindingResult();

    if (bindingResult.hasErrors()) {
      List<ObjectError> errors = bindingResult.getAllErrors();
      if (!errors.isEmpty()) {
        // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
        FieldError fieldError = (FieldError) errors.get(0);
        return ResponseEntity.ok()
            .body(RestResponse.builder().error(HeadCode.BAD_REQUEST)
                .message(fieldError.getField() + ":" + fieldError.getDefaultMessage()));
      }
    }

    return responseEntity;
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ResponseEntity<Object> responseEntity = super.handleBindException(ex, headers, status, request);

    BindingResult bindingResult = ex.getBindingResult();

    if (bindingResult.hasErrors()) {
      List<ObjectError> errors = bindingResult.getAllErrors();
      if (!errors.isEmpty()) {
        // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
        FieldError fieldError = (FieldError) errors.get(0);
        return ResponseEntity.ok()
            .body(RestResponse.builder().error(HeadCode.BAD_REQUEST)
                .message(fieldError.getField() + ":" + fieldError.getDefaultMessage()));
      }
    }

    return responseEntity;
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status,
      WebRequest request) {

    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
    }

    log.error("服务器运行出错：", ex);

    return ResponseEntity.ok()
        .body(RestResponse.builder().error(HeadCode.BAD_REQUEST)
            .message(ex.getMessage()));
  }

}
