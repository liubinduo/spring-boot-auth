package com.v1ok.commons;


public enum HeadCode {

  SUCCESS(200, "操作成功！"),
  /**
   * 服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理
   */
  ERROR(500, "服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理！"),

  /**
   * 请求参数有误
   */
  BAD_REQUEST(400, "请求参数有误"),
  /**
   * 当前请求需要用户验证
   */
  UN_AUTHORIZED(401, "当前请求需要用户验证"),
  /**
   * 请求失败，请求所希望得到的资源未被在服务器上发现
   */
  NOT_FIND(404, "请求失败，请求所希望得到的资源未被在服务器上发现！"),
  /**
   * 服务器已经理解请求，但是拒绝执行它
   */
  FORBIDDEN(403, "服务器已经理解请求，但是拒绝执行它"),

  NO_PERMISSION(701,"当前用户没有此操作权限");



  private final int code;

  private final String msg;

  HeadCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

}
