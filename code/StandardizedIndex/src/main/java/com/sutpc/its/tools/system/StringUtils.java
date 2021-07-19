package com.sutpc.its.tools.system;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  /**
   * 字符串模板替换，如把字符串里的{name}替换成map里key为name的value.
   */
  public static String tpl(String tplStr, Map<String, Object> data) {
    Matcher m = Pattern.compile("\\{([\\w\\.]*)\\}").matcher(tplStr);
    while (m.find()) {
      String group = m.group();
      group = group.replaceAll("\\{|\\}", "");
      String value = "";
      if (null != data.get(group)) {
        value = String.valueOf(data.get(group));
      }
      tplStr = tplStr.replace(m.group(), value);
    }
    return tplStr;
  }

  /**
   * paddingLeft.
   */
  public static String paddingLeft(int num, int totalLength) {
    return String.format("%0" + totalLength + "d", num);
  }

  /*public static void main(String[] a) {
    System.out.println(paddingLeft(20, 2));
  }*/
}
