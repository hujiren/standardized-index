package com.sutpc.its.dto;

import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:03 2020/11/13.
 * @Description
 * @Modified By:
 */
@Data
public class CheckParams {

  private int startDate;
  private int endDate;
  private String[] id;
  private int startPeriod;
  private int endPeriod;
  private String timePrecision;
  private String dateProperty;
  private String timeProperty;
  private int roadLevel;
  private String index;
}
