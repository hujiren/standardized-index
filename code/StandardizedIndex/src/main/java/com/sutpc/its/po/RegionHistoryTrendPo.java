package com.sutpc.its.po;

import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:36 2020/11/17.
 * @Description
 * @Modified By:
 */
@Data
public class RegionHistoryTrendPo {

  private String id;
  private String time;
  private double avgSpeed;
  private double morningSpeed;
  private double eveningSpeed;
  private double flatSpeed;
  private double avgTpi;
  private double morningTpi;
  private double eveningTpi;
  private double flatTpi;
}
