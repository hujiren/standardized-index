package com.sutpc.its.config;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:56 2020/11/3.
 * @Description
 * @Modified By:
 */
public class Config {

  public static String[] config_district_fid;
  private static volatile Config instance;

  private Config() {
  }

  public static Config getInstance() {
    if (instance == null) {
      synchronized (Config.config_district_fid) {
        instance = new Config();
      }
    }
    return instance;
  }

}
