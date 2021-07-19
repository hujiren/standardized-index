package com.sutpc.its.tools.system;

import java.security.MessageDigest;
import javax.servlet.http.HttpServletRequest;


public class SecurityUtils {

  /**
   * 跨站点请求伪造-检测,false为安全检测通过，true为检测不通过.
   */
  public static Boolean checkCsrf(HttpServletRequest request) {

    String sp = request.getServletPath();

    //页面请求不算
    if (SystemUtils.isPageRequest(sp)) {
      return false;
    }

    String referer = request.getHeader("Referer");

    if (referer == null) {
      Boolean find = false;

      String cfg = PropertyUtils.getProperty("noRefererUrlPrefix");
      if (cfg != null) {
        String[] cfgarr = cfg.split(",");
        for (int i = 0; i < cfgarr.length; i++) {
          if (cfgarr[i] != "") {
            if (sp.startsWith(cfgarr[i])) {
              find = true;
              break;
            }
          }
        }
      }

      if (!find) {
        return true;
      }
    }

    //只能从该系统发出请求
    if (referer != null) {
      if (!referer.startsWith(request.getScheme() + "://" + request.getServerName())) {
        return true;
      }
    }

    return false;
  }

  /**
   * md5.
   */
  public static String md5(String s) {
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
        'F'};
    try {
      byte[] btInput = s.getBytes("UTF-8");
      // 获得MD5摘要算法的 MessageDigest 对象
      MessageDigest mdInst = MessageDigest.getInstance("md5");
      // 使用指定的字节更新摘要
      mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
