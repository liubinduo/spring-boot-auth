package com.v1ok.auth.interceptor;

import com.v1ok.auth.AuthProperties;
import com.v1ok.auth.Generator;
import com.v1ok.auth.IContext;
import com.v1ok.auth.IUserContext;
import com.v1ok.commons.ContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenFeignInterceptor implements RequestInterceptor {

  final AuthProperties authProperties;

  public TokenFeignInterceptor(AuthProperties authProperties) {
    this.authProperties = authProperties;
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {
    IContext context = ContextHolder.getHolder().get();
    if(context == null){
      return;
    }
    IUserContext userContext = context.currentUser();
    if (userContext == null) {
      return;
    }
    String token = Generator.token(userContext, authProperties.getServiceKey());
    requestTemplate.header(AbstractInterceptor.HTTP_HEAD_TOKEN, token);
  }
}
