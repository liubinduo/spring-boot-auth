package com.v1ok.auth.interceptor;

import com.v1ok.auth.IUserContext;
import com.v1ok.commons.ContextHolder;
import com.v1ok.commons.Head;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.RequestValue;
import com.v1ok.commons.exception.AuthorityException;
import com.v1ok.commons.impl.DefaultContext;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public abstract class AbstractInterceptor {

  public static final String HTTP_HEAD_TOKEN = "X-token";
  public static final String TOKEN = "token";

  public AbstractInterceptor() {
  }

  protected abstract IUserContext parseToken(String token);

  protected abstract String generateToken(IUserContext context);

  protected Object exec(IUserContext userContext, ProceedingJoinPoint point) throws Throwable {

    Head head = this.getHead(point);
    if ((isGet() || isDelete()) && head != null) {
      head.setTenantId(userContext.getTenantId());
    }
    DefaultContext context = new DefaultContext(userContext);
    ContextHolder.getHolder().set(context);
    try {

      Object proceed = point.proceed();

      if (proceed instanceof IRestResponse) {
        IRestResponse<?> restResponse = (IRestResponse<?>) proceed;
        if (restResponse.getHead().getCode() == 200) {
          String token = generateToken(userContext);
          restResponse.getHead().setToken(token);
        }
      }

      return proceed;

    } finally {
      ContextHolder.getHolder().remove();
    }
  }


  protected <T> T getArg(JoinPoint pjp, Class<T> clazz) {
    Object[] args = pjp.getArgs();

    for (Object arg : args) {
      if (arg == null) {
        continue;
      }
      Class<? extends Object> argClass = arg.getClass();

      if (ClassUtils.isAssignable(argClass, clazz)) {
        return (T) arg;
      }
    }

    return null;
  }

  protected RequestValue<?> getRequestValue(JoinPoint point) {
    return this.getArg(point, RequestValue.class);
  }

  protected Head getHead(JoinPoint point) {
    return this.getArg(point, Head.class);
  }

  protected IUserContext getUserContext(RequestValue<?> requestValue) {

    if (requestValue == null) {
      requestValue = new RequestValue<Objects>();
    }

    Head head = requestValue.getHead();

    String token = getToken(head);

    if (StringUtils.isEmpty(token)) {
      throw new AuthorityException();
    }

    IUserContext userContext = parseToken(token);

    if (userContext == null) {
      throw new AuthorityException();
    }

    head.setTenantId(userContext.getTenantId());

    return userContext;
  }

  protected String getRequestMethod() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder
            .getRequestAttributes())).getRequest();
    return request.getMethod();
  }


  protected boolean isGet() {
    return "GET".equals(getRequestMethod());
  }

  protected boolean isDelete() {
    return "DELETE".equals(getRequestMethod());
  }

  protected String getToken(Head head) {
    String token;

    HttpServletRequest request = ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder
            .getRequestAttributes())).getRequest();

    if (request != null) {

      //浏览器的head头中的token优先级最高
      token = request.getHeader(HTTP_HEAD_TOKEN);
      if (StringUtils.isNotEmpty(token)) {
        return token;
      }

      //其次是url上的参数
      token = request.getParameter(TOKEN);
      if (StringUtils.isNotEmpty(token)) {
        return token;
      }

      //其次请求体里的token
      if (head != null) {
        token = head.getToken();
        if (StringUtils.isNoneBlank(token)) {
          return token;
        }
      }

    }

    return null;
  }
}
