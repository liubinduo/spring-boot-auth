package com.v1ok.commons;

import com.v1ok.auth.IContext;
import com.v1ok.auth.IUserContext;

public abstract class AbstractCtrl {

  protected IUserContext currentUserContext() {
    IContext context = ContextHolder.getHolder().get();
    return context.currentUser();
  }
}
