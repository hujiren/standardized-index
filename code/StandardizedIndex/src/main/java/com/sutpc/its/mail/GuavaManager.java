package com.sutpc.its.mail;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/*
 *@author cjl
 *@date 2020/11/14 16:37
 */
public class GuavaManager {

  public static Cache<String, Object> cache = CacheBuilder
      .newBuilder().maximumSize(100)
      .build();


  public static Cache<String, Object> mailCache = CacheBuilder
      .newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES)
      .build();
}
