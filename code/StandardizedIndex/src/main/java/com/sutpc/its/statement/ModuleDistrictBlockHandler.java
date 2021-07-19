package com.sutpc.its.statement;

import com.deepoove.poi.data.PictureRenderData;
import com.sutpc.its.dao.IModuleDistrictBlockDao;
import com.sutpc.its.po.WorkDayPeakBlockChartPo;
import com.sutpc.its.po.WorkDayPeakExtremaBlockInfoPo;
import com.sutpc.its.statement.bean.ModuleDistrictBlockValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:43 2020/5/27.
 * @Description
 * @Modified By:
 */
@Service
@Component
public class ModuleDistrictBlockHandler implements ModuleHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${tpi.tpiBlockWaterImagePath}")
  private String blockWaterImagePath;

  @Value("${tpi.tpiBlockMapGeoserverPath}")
  private String tpiBlockGeoserverPath;

  @Autowired
  private IModuleDistrictBlockDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_BLOCK.name().equals(type);
  }

  /**
   * 模块数据程序-街道交通运行.
   *
   * @param type 模块类型
   * @param param 参数
   * @return 结果
   */
  @Override
  public ModuleValue handle(String type, StatementParam param) {
    ModuleDistrictBlockValue value = new ModuleDistrictBlockValue();
    getSlowAndUnimpededCount(param, value);
    getMaxBlockInfo(param, value);
    getMinBlockInfo(param, value);
    getPeakBlockChartData(param, value);
    getBlockMapBase64(param, value);
    logger.info("result = {}", value);
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
    ModuleDistrictBlockValue data = value.getValue(ModuleDistrictBlockValue.class);
    data.setTitle(value.getTitle());
    String chart = data.getChart();
    //chart图塞入容器
    if (chart != null && chart.length() > 0) {
      //width:558,height:303 模板图片宽高
      BufferedImage bufferedImage = TpiUtils.base64ToBufferedImage(chart);
      data.setPicChart(new PictureRenderData(558, 303, ".png", bufferedImage));
    } else {
      //预留功能：待定给出示例图片，以免生成报告出错
    }
    String map = data.getMap();
    //街道运行指数图塞入容器
    if (map != null && map.length() > 0) {
      BufferedImage bufferedImage = TpiUtils.base64ToBufferedImage(map);
      data.setPicMap(new PictureRenderData(538, 380, ".png", bufferedImage));
    } else {
      //预留功能模块：待定给出示例图片到容器，以免生成的报告出错
    }

    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 获取缓行及以上街道数量和基本畅通以下街道数量.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  public void getSlowAndUnimpededCount(StatementParam param, ModuleDistrictBlockValue value) {
    int slowCount = dao.getSlowCount(param.getCurrentStartDate(), param.getCurrentEndDate(),
        param.getDistrictId());
    int unimpededCount = dao
        .getUnimpededCount(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setSlowCount(slowCount);
    value.setUnimpededCount(unimpededCount);
  }

  /**
   * 获取交通压力最大街道相关信息.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  public void getMaxBlockInfo(StatementParam param, ModuleDistrictBlockValue value) {
    WorkDayPeakExtremaBlockInfoPo po = dao
        .getMaxBlockInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setPeakMaxBlock(po.getName());
    value.setPeakMaxBlockSpeed(TpiUtils.getByDigit(po.getSpeed(), param.getSpeedDigit()));
    value.setPeakMaxBlockTpi(TpiUtils.getByDigit(po.getTpi(), param.getTpiDigit()));
    value.setPeakMaxBlockStatus(TpiUtils.getStatusByTpi(po.getTpi()));
  }

  /**
   * 获取交通压力最小（畅通）街道相关信息.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  public void getMinBlockInfo(StatementParam param, ModuleDistrictBlockValue value) {
    WorkDayPeakExtremaBlockInfoPo po = dao
        .getMinBlockInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setPeakMinBlock(po.getName());
    value.setPeakMinBlockSpeed(TpiUtils.getByDigit(po.getSpeed(), param.getSpeedDigit()));
    value.setPeakMinBlockTpi(TpiUtils.getByDigit(po.getTpi(), param.getTpiDigit()));
    value.setPeakMinBlockStatus(TpiUtils.getStatusByTpi(po.getTpi()));
  }

  /**
   * 获取高峰时段所有街道速度.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  public void getPeakBlockChartData(StatementParam param, ModuleDistrictBlockValue value) {
    List<WorkDayPeakExtremaBlockInfoPo> list = dao
        .getPeakBlockChartData(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    List<WorkDayPeakBlockChartPo> data = new ArrayList<WorkDayPeakBlockChartPo>();
    for (WorkDayPeakExtremaBlockInfoPo po : list) {
      WorkDayPeakBlockChartPo cpo = new WorkDayPeakBlockChartPo();
      cpo.setName(po.getName());
      cpo.setSpeed(TpiUtils.getByDigit(po.getSpeed(), param.getSpeedDigit()));
      data.add(cpo);
    }
    value.setList(data);
  }

  /**
   * 获取各街道高峰时段交通运行指数图.
   */
  public void getBlockMapBase64(StatementParam param, ModuleDistrictBlockValue value) {
    int startDate = param.getCurrentStartDate();
    int endDate = param.getCurrentEndDate();
    int districtId = param.getDistrictId();
    String url = tpiBlockGeoserverPath + TpiUtils.getBbox(String.valueOf(districtId))
        + "&srs=EPSG:4326&format=image%2Fjpeg"
        + "&TRANSPARENT=true&viewparams=from_date:"
        + startDate + ";to_date:" + endDate
        + ";district_fid:" + districtId;
    /*String url = String
        .format("s%%26viewparams=from_date:s%;to_date:s%;district_fid:s%;", tpiBlockGeoserverPath,
            startDate, endDate, districtId);*/
    BufferedImage bufferedImage = TpiUtils.getBufferedImageByUrl(url);
    try {
      //加上图例水印
      BufferedImage image = TpiUtils
          .watermark(bufferedImage, new File(blockWaterImagePath), 0, 0, 1.0f);
      String base64 = TpiUtils.bufferedImageToBase64(image);
      value.setMap(base64);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
