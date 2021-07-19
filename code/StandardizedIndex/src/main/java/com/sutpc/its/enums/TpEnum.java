package com.sutpc.its.enums;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 19:12 2020/5/11.
 * @Description
 * @Modified By:
 */
public enum TpEnum {
  findRealTimeAreaPeopleChart("获取实时客流曲线数据", "getRealTimeAreaPeopleCharts",
      "9dce8cb2f93448a7980ec6902bb9d8fc"),
  getAllDetectorInfo("", "getAllDetectorInfo", "4355eabc933045c28709ad389a7d1d75"),
  getDetectorSpeedAndPcuById("", "getDetectorSpeedAndPcuById", "1289f8a176bb434b8679873466f75caf","v02");

  /**
   * 用处含义.
   */
  private String meaning;
  /**
   * url后缀.
   */
  private String suffixUrl;
  /**
   * api密钥.
   */
  private String apiKey;

  private String version;
  TpEnum(String meaning, String suffixUrl, String apiKey) {
    this.suffixUrl = suffixUrl;
    this.apiKey = apiKey;
    this.meaning = meaning;
  }
  TpEnum(String meaning, String suffixUrl, String apiKey,String version) {
    this.suffixUrl = suffixUrl;
    this.apiKey = apiKey;
    this.meaning = meaning;
    this.version = version;
  }

  public String getSuffixUrl() {
    return suffixUrl;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getMeaning() {
    return meaning;
  }

  public String getVersion(){
    return version;
  }


}
