package com.cjt.util;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2020-07-21 17:33
 */
public class MsgContextHolder {

  private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

  /**
   *
   */
  public static void setAppName(String appName) {
    CONTEXT_HOLDER.set(appName);
  }

  /**
   *
   * @return
   */
  public static String getAppName() {
    return CONTEXT_HOLDER.get();
  }

}
