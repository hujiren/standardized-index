package com.sutpc.its.po;

import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 18:50 2020/9/23.
 * @Description
 * @Modified By:
 */
@Data
public class RoadJamLengthPo {

  private int roadId;
  private String roadName;
  private String dir;
  private double jamLength;
}
