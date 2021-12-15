package com.v1ok.auth.interceptor;

import com.v1ok.auth.AuthProperties;
import com.v1ok.auth.AuthVerify;
import com.v1ok.auth.Generator;
import com.v1ok.auth.IContext;
import com.v1ok.auth.IUserContext;
import com.v1ok.commons.ContextHolder;
import com.v1ok.commons.Head;
import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import com.v1ok.commons.RequestValue;
import com.v1ok.commons.annotation.AuthorityRequired;
import com.v1ok.commons.impl.DefaultContext;
import com.v1ok.commons.impl.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuthorityRequiredInterceptor extends AbstractInterceptor {


  final AuthProperties properties;

  public AuthorityRequiredInterceptor(AuthProperties properties) {
    this.properties = properties;
  }


  @Around("@annotation(com.v1ok.commons.annotation.AuthorityRequired) && @annotation(authority)")
  public Object interceptor(ProceedingJoinPoint point, AuthorityRequired authority)
      throws Throwable {

    RequestValue<?> value = getRequestValue(point);

    IUserContext userContext = getUserContext(value);

    // 权限判断
    if (StringUtils.isNotEmpty(authority.permissionCode())
        && !userContext.getPermissions().contains(authority.permissionCode())) {

      return RestResponse.builder().error(HeadCode.NO_PERMISSION);

    }

    return exec(userContext, point);
  }


  @Override
  protected IUserContext parseToken(String token) {
    String interfaceKey = properties.getInterfaceKey();
    return AuthVerify.parseUserContextAccessToken(token, interfaceKey);
  }

  @Override
  protected String generateToken(IUserContext context) {
    String interfaceKey = properties.getInterfaceKey();
    return Generator.accessToken(context, interfaceKey);
  }
}
