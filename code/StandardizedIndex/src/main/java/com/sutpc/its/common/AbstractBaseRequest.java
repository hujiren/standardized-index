package com.sutpc.its.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sutpc.its.enums.TpEnum;
import com.sutpc.its.po.BaseParams;
import com.sutpc.its.tools.HttpUtils;
import com.sutpc.its.tools.Utils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:58 2020/8/13.
 * @Description
 * @Modified By:
 */
public abstract class AbstractBaseRequest {

  @Value("${transpaas.prefixUrl}")
  private String prefixUrl;

  @Autowired
  private HttpUtils httpUtils;

  protected <T> List<T> queryListEntity(BaseParams params, TpEnum baseInfoEnum, Class<T> clazz) {
    JSONArray jsonArray = queryCommonJsonArray(params, baseInfoEnum);
    return Utils.jsonArrayToListEntity(jsonArray, clazz);
  }

  protected <T> T queryEntity(BaseParams params, TpEnum baseInfoEnum, Class<T> clazz) {
    JSONArray jsonArray = queryCommonJsonArray(params, baseInfoEnum);
    return jsonArray == null || jsonArray.size() == 0 ? null
        : Utils.jsonToEntity(clazz, jsonArray.get(0));
  }

  @SuppressWarnings("unchecked")
  protected JSONArray queryCommonJsonArray(BaseParams params, TpEnum baseInfoEnum) {
    Map<String, Object> map = JSON.parseObject(JSON.toJSONString(params), Map.class);
    return invokeTp(map, baseInfoEnum);
  }

  /**
   * 调用tp公共方法.
   */
  protected JSONArray invokeTp(Map<String, Object> paramMap, TpEnum myEnum) {
    String apiUrl = prefixUrl + myEnum.getSuffixUrl();
    String apiKey = myEnum.getApiKey();
    String apiDesc = myEnum.getMeaning();
    String version = myEnum.getVersion();
    ResponseEntity<JSONObject> entity = httpUtils.sendHttpPost(paramMap, apiUrl, apiKey, apiDesc,version);
    return entity != null ? entity.getBody().getJSONArray("data") : null;
  }

}
