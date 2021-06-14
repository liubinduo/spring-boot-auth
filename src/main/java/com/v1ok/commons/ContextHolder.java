package com.v1ok.commons;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liubinduo on 2017/5/15.
 */
public class ContextHolder extends ThreadLocal<IContext> {

  static ContextHolder USER_CONTEXT;

  final static Lock LOCK = new ReentrantLock();

  private ContextHolder() {
  }

  public static ContextHolder getHolder() {
    if (USER_CONTEXT == null) {
      LOCK.lock();
      try {
        if (USER_CONTEXT == null) {
          USER_CONTEXT = new ContextHolder();
          return USER_CONTEXT;
        }
      } finally {
        LOCK.unlock();
      }
    }
    return USER_CONTEXT;
  }

}
