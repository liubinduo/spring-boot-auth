package com.v1ok.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityRequired {

  String authorityCode() default "";

  /**
   * 权限编码 此编码必须与表permission中的code字段相对应
   */
  String permissionCode() default "";

}
