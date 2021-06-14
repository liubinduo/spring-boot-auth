package com.v1ok.commons;

import com.v1ok.auth.IUserContext;

/**
 * Created by xinli on 05/04/2017.
 */
public interface IContext {

  IUserContext currentUser();

}
