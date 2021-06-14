package com.v1ok.commons;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RequestValue<T> extends AbstractRequestValue<T> {


  @NotNull(message = "报文头不能为空！")
  private Head head;

  private final Map<String, Object> attributes;

  @Valid
  private T body;


  public RequestValue() {
    this.head = new Head();
    this.attributes = new HashMap<>();
  }

  public Head getHead() {
    return head;
  }

  public void setHead(Head head) {
    this.head = head;
  }

  public T getBody() {
    return body;
  }


  public void setBody(T body) {
    this.body = body;
  }

  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  public <T> T getAttribute(String key) {
    return (T) this.attributes.get(key);
  }

  public <T> T setAttribute(String key, T object) {
    return (T) this.attributes.put(key, object);
  }


}
