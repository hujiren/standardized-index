package com.sutpc.its.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 9:18 2019/7/4 0004.
 * @Modified By:
 */
public class JwtUtils {

  /**
   * . secret
   */
  private static final String SECRET = "gh19$qODBFrf";


  /**
   * . 根据token解析Claims
   */
  public static Claims getTokenBody(String token) throws Exception {
    try {
      return Jwts.parser()
          .setSigningKey(SECRET.getBytes("UTF-8"))
          .parseClaimsJws(token).getBody();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }


}
