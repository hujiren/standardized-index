package com.sutpc.its.tools;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:46 2020/8/26.
 * @Description
 * @Modified By:
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack<S, T> {

  /**
   * 定义默认回调方法.
   *
   * @param t n
   * @param s n
   */
  void callBack(S t, T s);
}