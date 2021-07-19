package com.sutpc.its.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sutpc.its.config.WxConfig;
import com.sutpc.its.model.AccessToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * .
 */
public class WeixinUtil {

  private static Logger log = Logger.getLogger(WeixinUtil.class);

  /**
   * 发起https请求并获取结果.
   *
   * @param requestUrl 请求地址
   * @param requestMethod 请求方式（GET、POST）
   * @param outputStr 提交的数据
   * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
   */
  public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    try {
      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new MyX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
      httpUrlConn.setSSLSocketFactory(ssf);

      httpUrlConn.setDoOutput(true);
      httpUrlConn.setDoInput(true);
      httpUrlConn.setUseCaches(false);
      // 设置请求方式（GET/POST）
      httpUrlConn.setRequestMethod(requestMethod);

      if ("GET".equalsIgnoreCase(requestMethod)) {
        httpUrlConn.connect();
      }
      // 当有数据需要提交时
      if (null != outputStr) {
        OutputStream outputStream = httpUrlConn.getOutputStream();
        // 注意编码格式，防止中文乱码
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }

      // 将返回的输入流转换成字符串
      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();
      // 释放资源
      inputStream.close();
      inputStream = null;
      httpUrlConn.disconnect();
      jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (ConnectException ce) {
      log.error("Weixin server connection timed out.");
    } catch (Exception e) {
      log.error("https request error:{}", e);
    }
    log.error("httpRequest():jsonObject=" + jsonObject);
    return jsonObject;
  }

  // 获取access_token的接口地址（GET） 限200（次/天）
  public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

  /**
   * 获取access_token.
   *
   * @param appid 凭证
   * @param appsecret 密钥
   */
  public static AccessToken getAccessToken(String appid, String appsecret) {
    AccessToken accessToken = null;

    String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
    // 如果请求成功
    if (null != jsonObject) {
      try {
        accessToken = new AccessToken();
        accessToken.setToken(jsonObject.getString("access_token"));
        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
      } catch (JSONException e) {
        accessToken = null;
        // 获取token失败
        // log.error("获取token失败 errcode:{} errmsg:{}",
        // jsonObject.getInt("errcode"),
        // jsonObject.getString("errmsg"));
      }
    }
    return accessToken;
  }

  private static String getAccessToken() throws Exception {
    log.error("getAccessToken():appid=" + WxConfig.appid + "&appsecret=" + WxConfig.appsecret);
    String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
        + WxConfig.appid + "&secret=" + WxConfig.appsecret;

    ObjectMapper objectMapper = new ObjectMapper();

    JsonNode json = objectMapper
        .readValue(httpRequest(url, "GET", null).toString(), JsonNode.class);
    log.error("getAccessToken():json=" + json);
    return json.get("access_token").asText();
  }

  /**
   * 方法名：getWxConfig 详述：获取微信的配置信息.
   *
   * @return 说明返回值含义
   */
  public static Map<String, Object> getWxConfig(HttpServletRequest request) throws Exception {
    String jsapiTicket = "";
    ObjectMapper objectMapper = new ObjectMapper();
    String url =
        "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getAccessToken()
            + "&type=jsapi";
    JsonNode json = objectMapper
        .readValue(httpRequest(url, "GET", null).toString(), JsonNode.class);
    log.error("getWxConfig():json=" + json);
    if (json != null) {
      jsapiTicket = json.get("ticket").asText();
    }
    log.error("getWxConfig():jsapi_ticket=" + jsapiTicket);
    String signature = "";
    // 注意这里参数名必须全部小写，且必须有序
    String requestUrl = request.getParameter("url");
    // 必填，生成签名的随机串
    String nonceStr = UUID.randomUUID().toString();
    // 必填，生成签名的时间戳
    String timestamp = Long.toString(System.currentTimeMillis() / 1000);
    String sign =
        "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp
            + "&url=" + requestUrl;
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(sign.getBytes("UTF-8"));
      log.error("getWxConfig():crypt=" + crypt);
      signature = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    Map<String, Object> ret = new HashMap<String, Object>();
    // ret.put("appId", Config.wxappId);
    ret.put("appId", WxConfig.appid);
    ret.put("timestamp", timestamp);
    ret.put("nonceStr", nonceStr);
    ret.put("signature", signature);
    return ret;
  }

  /**
   * 方法名：byteToHex.详述：字符串加密辅助方法.
   *
   * @return 说明返回值含义
   */
  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;

  }

}
