package com.sutpc.its.tools.system;


public class PropertyUtils {

  /**
   * getProperty.
   */
  public static String getProperty(String key) {

    return (String) CustomizedPropertyPlaceholderConfigurer.getContextProperty(key);
  }

  /**
   * getProperty.
   */
  public static String getProperty(String key, String defaultValue) {

    String v = getProperty(key);
    if (null == v) {
      return defaultValue;
    }
    return v;
  }


}
