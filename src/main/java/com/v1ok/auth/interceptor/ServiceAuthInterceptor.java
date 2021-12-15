package com.v1ok.auth.interceptor;

import com.v1ok.auth.AuthProperties;
import com.v1ok.auth.AuthVerify;
import com.v1ok.auth.Generator;
import com.v1ok.auth.IUserContext;
import com.v1ok.commons.RequestValue;
import com.v1ok.commons.annotation.ServiceAuth;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceAuthInterceptor extends AbstractInterceptor {

  final AuthProperties properties;

  public ServiceAuthInterceptor(AuthProperties properties) {
    this.properties = properties;
  }

  @Around("@annotation(com.v1ok.commons.annotation.ServiceAuth) && @annotation(serviceAuth)")
  public Object Interceptor(ProceedingJoinPoint point, ServiceAuth serviceAuth) throws Throwable {

    RequestValue<?> value = getRequestValue(point);

    IUserContext userContext = getUserContext(value);

    return exec(userContext, point);
  }

  @Override
  protected IUserContext parseToken(String token) {
    String serviceKey = properties.getServiceKey();
    return AuthVerify.parseUserContextToken(token, serviceKey);
  }

  @Override
  protected String generateToken(IUserContext context) {
    String serviceKey = properties.getServiceKey();
    return Generator.token(context, serviceKey);
  }
}
