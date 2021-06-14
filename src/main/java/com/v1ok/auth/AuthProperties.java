package com.v1ok.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

  /**
   * 服务私匙
   */
  String serviceKey;

  /**
   * 接口私匙
   */
  String interfaceKey;

}
