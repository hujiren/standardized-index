package com.sutpc.its.filter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns = {"/**"})
public class RequestFilter implements Filter {

  private final static int STRING_LENGTH = 7580;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  /**
   * 过滤sql注入等特殊非法字符以及数据库操作关键字等.
   */
  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
        (HttpServletRequest) servletRequest);
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    //跨站点请求伪造
//    if (!"GET".equals(request.getMethod())) {
//      String referrer = request.getHeader("referer");
//      if (referrer != null) {
//        String referrerHost = new URL(referrer).getHost();
//        String expectedHost = new URL(request.getRequestURL().toString()).getHost();
//        if (!referrerHost.contains(expectedHost)) {
//          throw new IOException("请使用正确的请求头参数！");
//        }
//      }
//    }

    //加密会话（SSL）Cookie 中缺少 Secure 属性 解决方案
    String jSessionId = request.getSession().getId();
    response.setHeader("Set-Cookie", "JSESSIONID=" + jSessionId + "; Secure;HttpOnly;");

    // 获得所有请求参数名
    Enumeration params = request.getParameterNames();
    String sql = "";
    List<String> stringList = new ArrayList<>();
    while (params.hasMoreElements()) {
      // 得到参数名
      String name = params.nextElement().toString();
      // 得到参数对应值
      String[] value = request.getParameterValues(name);
      for (int i = 0; i < value.length; i++) {
        sql = sql + value[i];
        stringList.add(value[i]);
      }
    }
    for (String str : stringList) {
      int length = str.length();
      //postman支持的单个参数最大值长度，超出就不能继续测试了，所以暂且定长度为这么多。
      if (length > STRING_LENGTH) {
        throw new IOException("请使用正确的请求访问参数！");
      }
    }
    if (sqlValidate(sql)) {
      throw new IOException("请使用正确的请求访问参数！");
      //filterChain.doFilter(request, response);
    } else {
      filterChain.doFilter(request, response);
    }
    //filterChain.doFilter(xssRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }

  /**
   * 参数校验
   */
  public static boolean sqlValidate(String str) {
    str = str.toLowerCase();//统一转为小写
    //String badStr="";
    String badStr = "select|update|and|delete|insert|truncate|char|varchar|into|set|substr|ascii|declare|cast|exec|count|master|into|drop|execute|table|+|while|../|$|e-308|0x|@|CR|LF";
    String[] badStrs = badStr.split("\\|");
    for (int i = 0; i < badStrs.length; i++) {
      //循环检测，判断在请求参数当中是否包含SQL关键字
      if (str.indexOf(badStrs[i]) >= 0) {
        return true;
      }
    }
    return false;
  }

  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteHost();
    }
    return ip;
  }

}