package com.v1ok.commons;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class OpenIdUtil {

  private static final String VERSION = "$01";

  private static final String CHARACTER_SEQUENCE = ".-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final char[] CHARS = CHARACTER_SEQUENCE.toCharArray();


  public static String createOpenId(String userId, String securityKey) {

    Validate.notBlank(userId, "The userId must not be blank.");
    Validate.notBlank(securityKey, "The securityKey must not be blank.");

    Validate.isTrue(validate(userId), "userId not at chars '" + CHARACTER_SEQUENCE + "'");

    char[] chars = userId.toCharArray();

    StringBuilder openIdBuilder = new StringBuilder(VERSION);

    for (char c : chars) {
      //计算在映射字符中的位置
      int indexOfChars = ArrayUtils.indexOf(CHARS, c);
      //增加偏移量
      int i = (indexOfChars + offset(securityKey)) % CHARS.length;

      openIdBuilder.append(CHARS[i]);
    }

    return openIdBuilder.toString();
  }

  public static String parseOpenId(String openId, String securityKey) {

    Validate.notBlank(openId, "The openId must not be blank.");
    Validate.notBlank(securityKey, "The securityKey must not be blank.");

    openId = StringUtils.substring(openId, VERSION.length());

    Validate.isTrue(validate(openId), "openId not at chars '" + CHARACTER_SEQUENCE + "'");

    char[] chars = openId.toCharArray();

    StringBuilder openIdBuilder = new StringBuilder();

    for (char c : chars) {
      //计算在映射字符中的位置
      int indexOfChars = ArrayUtils.indexOf(CHARS, c);
      //增加偏移量
      int i = (indexOfChars + CHARS.length - offset(securityKey)) % CHARS.length;

      openIdBuilder.append(CHARS[i]);
    }

    return openIdBuilder.toString();
  }

  private static int offset(String securityKey) {
    int offset = 0;

    char[] chars = securityKey.toCharArray();
    for (char c : chars) {
      offset += c;
      offset %= chars.length;
    }
    return offset;
  }

  private static boolean validate(String str) {
    char[] chars = str.toCharArray();
    for (char c : chars) {
      if (!ArrayUtils.contains(CHARS, c)) {
        return false;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    String userId = "2B346DACBC21000";
    String securityKey = "MkIwRjlBNDU1NDIxMDAw";
    System.out.println(userId);
    String openId = createOpenId(userId, securityKey);
    System.out.println(openId);
    String parseOpenId = parseOpenId(openId, securityKey);
    System.out.println(parseOpenId);
  }

}
