package com.sutpc.its.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:47 2020/7/21.
 * @Description
 * @Modified By:
 */
@Data
@Component
@ConfigurationProperties(prefix = "tpi.poi")
@PropertySource(value = "application.properties")
public class DataConfig {

  private String shenzhenwan;
  private String xianhu;
  private String lianhuashan;
  private String gouwugongyuan;
  private String shijiezhichuagn;
  private String futianstation;
  private String shenzhenzhongxin;
  private String luohukouan;
  private String shenzhenbei;
  private String jichang;
  private String huaqiaocheng;
  private String dameisha;
  private String huaqiangbei;

  /**
   * 通过id获取配置信息.
   */
  public String getStringById(int id) {
    switch (id) {
      case 56:
        return shenzhenwan;
      case 39:
        return xianhu;
      case 23:
        return lianhuashan;
      case 19:
        return gouwugongyuan;
      case 54:
        return shijiezhichuagn;
      case 15:
        return futianstation;
      case 24:
        return shenzhenzhongxin;
      case 33:
        return luohukouan;
      case 122:
        return shenzhenbei;
      case 77:
        return jichang;
      case 68:
        return huaqiaocheng;
      case 69:
        return dameisha;
      case 18:
        return huaqiangbei;
      default:
        return "";
    }
  }
}
