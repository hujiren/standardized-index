package com.sutpc.its.po.v6;

import com.sutpc.its.tools.Utils;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:27 2020/10/18.
 * @Description
 * @Modified By:
 */
@Data
public class UserDefinedInfoPo {

  @ApiModelProperty(value = "id")
  private String id;
  @ApiModelProperty(value = "日期")
  private String time;
  @ApiModelProperty(value = "标题名称")
  private String name;
  @ApiModelProperty(value = "组成区域")
  private String region;
  @ApiModelProperty(value = "面积")
  private double area;

  private Clob clobPoints;
  @ApiModelProperty(value = "框选范围")
  private String points;

  private Clob clobLinks;
  @ApiModelProperty(value = "点选小路段集")
  private String links;

  private Clob clobRoads;
  @ApiModelProperty(value = "大路段")
  private String roads;

  private Clob clobSections;
  @ApiModelProperty(value = "中路段")
  private String sections;


  @ApiModelProperty(value = "路段总长度")
  private double lengths;

  private String list;

  @ApiModelProperty(value = "采集类型.1-框选，2-点选")
  private int type;
  @ApiModelProperty(value = "创建时间")
  private Date create_time;
  @ApiModelProperty(value = "创建人")
  private String creator;

  public void setClobPoints(Clob clobPoints) {
    this.clobPoints = clobPoints;
    try {
      this.points = Utils.clobToString(clobPoints);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setClobLinks(Clob clobLinks) {
    this.clobLinks = clobLinks;
    try {
      this.links = Utils.clobToString(clobLinks);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setClobRoads(Clob clobRoads) {
    this.clobRoads = clobRoads;
    try {
      this.roads = Utils.clobToString(clobRoads);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setClobSections(Clob clobSections) {
    this.clobSections = clobSections;
    try {
      this.sections = Utils.clobToString(clobSections);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
