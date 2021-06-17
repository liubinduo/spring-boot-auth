package com.v1ok.commons.error;

import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.impl.RestResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalMethodArgumentNotValidExceptionHandler {

  @ResponseStatus(HttpStatus.OK) //设置状态码为 200
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public IRestResponse<?> paramExceptionHandler(MethodArgumentNotValidException e) {
    BindingResult exceptions = e.getBindingResult();
    // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
    if (exceptions.hasErrors()) {
      List<ObjectError> errors = exceptions.getAllErrors();
      if (errors != null && !errors.isEmpty()) {
        // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
        FieldError fieldError = (FieldError) errors.get(0);
        return RestResponse.builder().error(HeadCode.BAD_REQUEST)
            .message(fieldError.getDefaultMessage());
      }
    }
    return RestResponse.builder().error(HeadCode.ERROR);
  }

}
