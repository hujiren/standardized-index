package com.sutpc.its.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.BeanUtils;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:44 2020/8/26.
 * @Description
 * @Modified By:
 */
public class BeanCopyUtil extends BeanUtils {

  /**
   * 集合数据的拷贝.
   *
   * @sources: 数据源类
   * @target: 目标类::new(eg: UserVO::new)
   */
  public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
    return copyListProperties(sources, target, null);
  }


  /**
   * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）.
   *
   * @sources: 数据源类
   * @target: 目标类::new(eg: UserVO::new)
   * @callBack: 回调函数
   */
  public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target,
      BeanCopyUtilCallBack<S, T> callBack) {
    List<T> list = new ArrayList<>(sources.size());
    for (S source : sources) {
      T t = target.get();
      copyProperties(source, t);
      list.add(t);
      if (callBack != null) {
        // 回调
        callBack.callBack(source, t);
      }
    }
    return list;
  }
}
