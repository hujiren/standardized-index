package com.sutpc.its.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@ApiModel("接口统一返回结果")
public class HttpResult<T> {

  @ApiModelProperty(value = "返回码")
  private String code;
  @ApiModelProperty(value = "附加消息")
  private String msg;
  @ApiModelProperty(value = "附加数据")
  private T data;


  public HttpResult() {
    code = "200";
    msg = "success";
  }

  /**
   * ok.
   */
  public static HttpResult<Object> ok() {
    HttpResult<Object> r = new HttpResult<Object>();
    r.setData(null);
    return r;
  }

  /**
   * ok.
   */
  public static <T> HttpResult<T> ok(T data) {
    HttpResult<T> r = new HttpResult<T>();
    setPrecision(data);
    r.setData(data);
    return r;
  }

  public static <T> void setPrecision(T data) {
    //判断类型
    if (data instanceof List) {
      for (Object map : (List) data) {
        if (map instanceof Map) {
          setValue(map);
        }
      }
    }
    if (data instanceof Map) {
      setValue(data);
    }

  }

  public static void setValue(Object map) {
    if (((Map) map).containsKey("TPI")) {
      String tpi = String.valueOf(((Map) map).get("TPI"));
      int point = tpi.indexOf(".");
      ;
      if (!tpi.contains(".")) {
        tpi = tpi + ".00";
      } else if (tpi.length() - 1 - point == 1) {
        tpi = tpi + "0";
      }
      ((Map) map).put("TPI", tpi);
    }
    if (((Map) map).containsKey("tpi")) {
      String tpi = String.valueOf(((Map) map).get("tpi"));
      int point = tpi.indexOf(".");
      if (!tpi.contains(".")) {
        tpi = tpi + ".00";
      } else if (tpi.length() - 1 - point == 1) {
        tpi = tpi + "0";
      }
      ((Map) map).put("tpi", tpi);
    }
    if (((Map) map).containsKey("SPEED")) {
      String speed = String.valueOf(((Map) map).get("SPEED"));
      int point = speed.indexOf(".");
      if (!speed.contains(".")) {
        speed = speed + ".0";
      }
      ((Map) map).put("SPEED", speed);
    }
    if (((Map) map).containsKey("speed")) {
      String speed = String.valueOf(((Map) map).get("speed"));
      int point = speed.indexOf(".");
      if (!speed.contains(".")) {
        speed = speed + ".0";
      }
      ((Map) map).put("speed", speed);
    }
  }

  /**
   * ok.
   */
  public static <T> HttpResult<T> ok(T data, String msg) {
    HttpResult<T> r = new HttpResult<T>();
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  /**
   * ok.
   */
  public static <T> HttpResult<T> ok(String code, T data, String msg) {
    HttpResult<T> r = new HttpResult<T>();
    r.setCode(code);
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  /**
   * error.
   */
  public static HttpResult<Object> error() {
    HttpResult<Object> r = new HttpResult<Object>();
    r.setCode("500");
    r.setMsg("未知异常，请联系管理员");
    r.setData(null);
    return r;
  }

  /**
   * error.
   */
  public static HttpResult<Object> error(String msg) {
    HttpResult<Object> r = new HttpResult<Object>();
    r.setCode("500");
    r.setMsg(msg);
    return r;
  }

  /**
   * error.
   */
  public static <T> HttpResult<T> error(T data, String msg) {
    HttpResult<T> r = new HttpResult<T>();
    r.setCode("500");
    r.setData(data);
    r.setMsg(msg);
    return r;
  }

  /**
   * error.
   */
  public static <T> HttpResult<T> error(String code, T data, String msg) {
    HttpResult<T> r = new HttpResult<T>();
    r.setCode(code);
    r.setData(data);
    r.setMsg(msg);
    return r;
  }


}
