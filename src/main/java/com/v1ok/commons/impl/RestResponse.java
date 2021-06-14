package com.v1ok.commons.impl;

import com.v1ok.commons.Head;
import com.v1ok.commons.HeadCode;
import com.v1ok.commons.IRestResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

/**
 * Created by xinli on 10/04/2017.
 */
@ToString
public class RestResponse<V> extends AbstractMappingJacksonValue<V> implements IRestResponse<V> {

  private final Map<String, Object> value;

  private RestResponse(Object body) {
    super(body);
    this.value = new HashMap<>();
    this.value.put(HEAD_KEY, new Head());
    this.value.put(BODY_KEY, body);
    this.setValue(this.value);


  }

  public static <T> RestResponse<T> builder() {
    RestResponse<T> response = new RestResponse<>(null);
    response.success(true);
    return response;
  }

  public static <T> RestResponse<T> builder(T body) {
    RestResponse<T> response = new RestResponse<>(body);
    response.success(true);
    return response;
  }


  public RestResponse<V> success(boolean isSuccess) {
    Head head = this.getHead();
    if (isSuccess) {
      head.setCode(HeadCode.SUCCESS.getCode());
      head.setMsg(HeadCode.SUCCESS.getMsg());
    } else {
      head.setCode(HeadCode.ERROR.getCode());
      head.setMsg(HeadCode.ERROR.getMsg());
    }
    return this;
  }


  @Override
  public RestResponse<V> data(V data) {
    getContext().put(BODY_KEY, data);
    return this;
  }

  public RestResponse<V> serializeData(V data) {
    getContext().put(BODY_KEY, data);

    return this;
  }


  public RestResponse<V> message(String message) {
    this.getHead().setMsg(message);
    return this;
  }

  public RestResponse<V> error() {
    return error(HeadCode.ERROR);
  }

  public RestResponse<V> error(HeadCode headCode) {
    this.getHead().setCode(headCode.getCode());
    this.getHead().setMsg(headCode.getMsg());
    return this;
  }

  @Override
  public Head getHead() {
    return (Head) getContext().get(HEAD_KEY);
  }

  public void setHead(Head head) {
    getContext().put(HEAD_KEY, head);
  }

  @SuppressWarnings("unchecked")
  public V getBody() {
    return (V) getContext().get(BODY_KEY);
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> getContext() {
    return (Map<String, Object>) this.getValue();
  }

}
