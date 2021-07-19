package com.sutpc.its.statement;

import com.deepoove.poi.data.PictureRenderData;
import com.sutpc.its.dao.IModuleDistrictMainRoadDao;
import com.sutpc.its.dto.WorkDayPeakMainRoadDto;
import com.sutpc.its.po.WorkDayPeakMainRoadPo;
import com.sutpc.its.statement.bean.ModuleDistrictMainRoadValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 报告模板处理程序-主要干道交通运行.
 *
 * @Author: zuotw
 * @Date: created on 16:36 2020/5/27.
 * @Description
 * @Modified By:
 */
@Component
public class ModuleDistrictMainRoadHandler implements ModuleHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${tpi.tpiMainRoadWaterImagePath}")
  private String tpiMainRoadWaterImagePath;

  @Value("${tpi.tpiMainRoadGeoserverPath}")
  private String tpiMainRoadGeoserverPath;

  @Autowired
  private IModuleDistrictMainRoadDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_MAIN_ROAD.name().equals(type);
  }

  /**
   * 模块数据程序.
   *
   * @param type 模块类型
   * @param param 参数
   * @return 结果
   */
  @Override
  public ModuleValue handle(String type, StatementParam param) {
    ModuleDistrictMainRoadValue value = new ModuleDistrictMainRoadValue();
    calSpeedAndRatio(param, value);
    calMainRoadInfo(param, value);
    getMainRoadBase64(param, value);
    return new ModuleValue(value, type);
  }

  /**
   * 根据模板和值生成模块word.
   *
   * @param value 值
   * @param template 模板路径
   * @return 生成的word路径
   */
  @Override
  public String buildWord(ModuleValue value, ModuleTemplate template) {
    ModuleDistrictMainRoadValue data = value.getValue(ModuleDistrictMainRoadValue.class);
    data.setTitle(value.getTitle());
    String mainRoadImage = data.getMainRoadImage();
    if (mainRoadImage != null && mainRoadImage.length() > 0) {
      //width:556,height:308 模板图片宽高
      BufferedImage bufferedImage = TpiUtils.base64ToBufferedImage(mainRoadImage);
      data.setPicMainRoadImage(new PictureRenderData(556, 400, ".png", bufferedImage));
    } else {
      //预留功能：待定给出示例图片，以免生成报告出错
    }
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 工作日高峰时段主要干道高峰时段平均速度以及环比.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  public void calSpeedAndRatio(StatementParam param, ModuleDistrictMainRoadValue value) {
    double mainRoadSpeed = dao
        .getMainRoadSpeed(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setMainRoadSpeed(TpiUtils.getByDigit(mainRoadSpeed, param.getSpeedDigit()));
    double lastMainRoadSpeed = dao
        .getMainRoadSpeed(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    double cycleSpeedRatio = TpiUtils.calculateGrowth(lastMainRoadSpeed, mainRoadSpeed);
    value.setCycleMainRoadRatio(TpiUtils.getByDigit(cycleSpeedRatio, param.getRatioDigit()));
  }

  /**
   * 主要干道交通运行速度表以及计算.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  public void calMainRoadInfo(StatementParam param, ModuleDistrictMainRoadValue value) {
    List<WorkDayPeakMainRoadDto> thisList = dao
        .getMainRoadInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    List<WorkDayPeakMainRoadDto> lastList = dao
        .getMainRoadInfo(param.getCycleStartDate(), param.getCycleEndDate(), param.getDistrictId());
    List<WorkDayPeakMainRoadPo> data = new ArrayList<WorkDayPeakMainRoadPo>();
    for (WorkDayPeakMainRoadDto thisDto : thisList) {
      int thisId = thisDto.getId();
      double thisSpeed = thisDto.getSpeed();
      thisDto.setSpeed(TpiUtils.getByDigit(thisSpeed, param.getSpeedDigit()));
      for (WorkDayPeakMainRoadDto lastDto : lastList) {
        int lastId = lastDto.getId();
        if (thisId == lastId) {
          double lastSpeed = lastDto.getSpeed();
          double ratio = TpiUtils
              .getByDigit(TpiUtils.calculateGrowth(lastSpeed, thisSpeed), param.getRatioDigit());
          thisDto.setRatio(ratio);
          WorkDayPeakMainRoadPo po = new WorkDayPeakMainRoadPo();
          BeanUtils.copyProperties(thisDto, po);
          data.add(po);
          continue;
        }
      }
    }
    //按照环比倒序排序
    Collections.sort(data, new Comparator<WorkDayPeakMainRoadPo>() {
      @Override
      public int compare(WorkDayPeakMainRoadPo o1, WorkDayPeakMainRoadPo o2) {
        double d1 = o1.getRatio();
        double d2 = o2.getRatio();
        String r1 = String.valueOf(d1);
        String r2 = String.valueOf(d2);
        if (d1 < 0 & d2 < 0) {
          return r1.compareTo(r2);
        } else {
          return r2.compareTo(r1);
        }
      }
    });
    for (int i = 0; i < data.size(); i++) {
      WorkDayPeakMainRoadPo po = data.get(i);
      po.setIndex(i + 1);
    }
    value.setList(data);
    if (data.size() > 0) {
      WorkDayPeakMainRoadPo maxPo = data.get(0);
      WorkDayPeakMainRoadPo minPo = data.get(data.size() - 1);
      value.setUpMaxRoad(String.format("%s(%s)", maxPo.getRoadName(), maxPo.getDirName()));
      value.setCycleUpMaxRatio(maxPo.getRatio());
      value.setDownMaxRoad(String.format("%s(%s)", minPo.getRoadName(), minPo.getDirName()));
      value.setCycleDownMaxRatio(minPo.getRatio());
    }
  }

  /**
   * 主要干道高峰时段平均速度值.
   *
   * @param param 模块参数
   * @param value 模板值
   */
  public void getMainRoadBase64(StatementParam param, ModuleDistrictMainRoadValue value) {
    int startDate = param.getCurrentStartDate();
    int endDate = param.getCurrentEndDate();
    int districtId = param.getDistrictId();
    String url = tpiMainRoadGeoserverPath + TpiUtils.getBbox(String.valueOf(districtId))
        + "&srs=EPSG:4326&format=image%2Fjpeg"
        + "&TRANSPARENT=true&viewparams=from_date:" + startDate + ";to_date:" + endDate
        + ";district_fid:" + districtId;
    BufferedImage bufferedImage = TpiUtils.getBufferedImageByUrl(url);
    try {
      //加上图例水印
      BufferedImage image = TpiUtils
          .watermark(bufferedImage, new File(tpiMainRoadWaterImagePath), 0, 0, 1.0f);
      String base64 = TpiUtils.bufferedImageToBase64(image);
      value.setMainRoadImage(base64);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
