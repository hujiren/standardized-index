package com.sutpc.its.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 19:58 2020/3/31.
 * @Description
 * @Modified By:
 */
@Component
@Slf4j
public class HttpUtils {

  private RestTemplate restTemplate = new RestTemplate();

  /**
   * getHerder.
   */
  public static HttpEntity<String> getHerder(String requestBody, String apiKey) {
    HttpHeaders requestHeaders = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    requestHeaders.setContentType(type);
    requestHeaders.add("X-API-KEY", apiKey);
    return new HttpEntity<String>(requestBody, requestHeaders);
  }

  /**
   * 发送HTTP POST请求.
   *
   * @param dataObj 请求体数据（not null）
   * @param apiUrl api链接（not null）
   * @param apiKey api秘钥（can be null）
   * @param desc 调用描述，用于日志（not null）
   * @return 响应数据 （not null）
   */
  public ResponseEntity<JSONObject> sendHttpPost(Object dataObj, String apiUrl, String apiKey,
      String desc) {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
    requestHeaders.add("X-API-KEY", apiKey);
    HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dataObj), requestHeaders);
    log.debug("[{}] [httpEntity={}]", desc, JSON.toJSONString(httpEntity));
    ResponseEntity<JSONObject> responseEntity = restTemplate
        .postForEntity(apiUrl, httpEntity, JSONObject.class);
    log.debug("[{}] [responseEntity={}]", desc, JSON.toJSONString(responseEntity));

    return responseEntity;
  }

  /**
   * 发送HTTP POST请求.
   *
   * @param dataObj 请求体数据（not null）
   * @param apiUrl api链接（not null）
   * @param apiKey api秘钥（can be null）
   * @param desc 调用描述，用于日志（not null）
   * @return 响应数据 （not null）
   */
  public ResponseEntity<JSONObject> sendHttpPost(Object dataObj, String apiUrl, String apiKey,
      String desc, String version) {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
    requestHeaders.add("X-API-KEY", apiKey);
    requestHeaders.add("X-TransPaaS-Version", version);
    HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(dataObj), requestHeaders);
    log.debug("[{}] [httpEntity={}]", desc, JSON.toJSONString(httpEntity));
    ResponseEntity<JSONObject> responseEntity = restTemplate
        .postForEntity(apiUrl, httpEntity, JSONObject.class);
    log.debug("[{}] [responseEntity={}]", desc, JSON.toJSONString(responseEntity));

    return responseEntity;
  }
}
