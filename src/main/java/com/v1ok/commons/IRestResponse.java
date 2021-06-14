package com.v1ok.commons;

import java.util.Set;

/**
 * Created by liubinduo on 2017/5/16.
 */
public interface IRestResponse<Body> {

  String HEAD_KEY = "head";
  String BODY_KEY = "body";

  IRestResponse<Body> data(Body data);

  Head getHead();

  Body getBody();
}
