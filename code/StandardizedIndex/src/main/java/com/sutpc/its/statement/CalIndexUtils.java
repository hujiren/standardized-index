package com.sutpc.its.statement;

import com.sutpc.its.dto.HotpotInfoDto;
import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.po.HotpotInfoPo;
import com.sutpc.its.po.RoadSectionChangeInfoPo;
import com.sutpc.its.po.RoadSectionChangePo;
import com.sutpc.its.statement.bean.ModuleDistrictHotpotBaseValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.TpiUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.BeanUtils;

/**
 * 计算指标工具类.
 *
 * @Author: zuotw
 * @Date: created on 11:41 2020/6/1.
 * @Description
 * @Modified By:
 */
public class CalIndexUtils {

  /**
   * 计算热点区域数据.
   */
  public static void calHotpotRatio(List<HotpotInfoDto> thisList, List<HotpotInfoDto> lastList,
      ModuleDistrictHotpotBaseValue value, StatementParam param) {
    List<HotpotInfoPo> data = new ArrayList<>();
    if (thisList != null && thisList.size() > 0) {
      double totalTpi = 0.00;
      double totalRatio = 0.00;
      for (HotpotInfoDto thisDto : thisList) {
        double thisTpi = thisDto.getTpi();
        int thisId = thisDto.getId();
        if (lastList != null && lastList.size() > 0) {
          for (HotpotInfoDto lastDto : lastList) {
            int lastId = lastDto.getId();
            if (thisId == lastId) {
              totalTpi = totalTpi + thisTpi;
              HotpotInfoPo po = new HotpotInfoPo();
              BeanUtils.copyProperties(thisDto, po);
              double lastTpi = lastDto.getTpi();
              double ratio = TpiUtils.calculateGrowth(lastTpi, thisTpi, param.getRatioDigit());
              totalRatio = totalRatio + ratio;
              po.setRatio(ratio);
              data.add(po);
            }
          }
        }
      }
      if (data.size() > 0) {
        value.setList(data);
        double avgTpi = TpiUtils.getByDigit(totalTpi / data.size(), param.getTpiDigit());
        double avgRatio = TpiUtils.getByDigit(totalRatio / data.size(), param.getRatioDigit());
        String status = TpiUtils.getStatusByTpi(avgTpi);
        value.setRatio(avgRatio);
        value.setStatus(status);
        value.setTpi(avgTpi);
      }
    }
  }

  /**
   * .
   */
  public static List<RoadSectionInfoDto> calPickList(List<RoadSectionInfoDto> list,
      StatementParam param) {
    for (RoadSectionInfoDto dto : list) {
      double speed = dto.getSpeed();
      int typeId = dto.getTypeId();
      dto.setTpi(TpiUtils.getByDigit(TpiUtils.speedToTpi(speed, typeId), param.getTpiDigit()));
      dto.setSpeed(TpiUtils.getByDigit(speed, param.getSpeedDigit()));
    }
    //按指数倒序，并去前五条记录作为运算
    if (list.size() > 0) {
      Collections.sort(list, new Comparator<RoadSectionInfoDto>() {
        @Override
        public int compare(RoadSectionInfoDto o1, RoadSectionInfoDto o2) {
          double tpi1 = o1.getTpi();
          double tpi2 = o2.getTpi();
          //          if (tpi2 >= tpi1) {
          // return 1;
          // } else {
          //return -1;
          //}

          BigDecimal j = BigDecimal.valueOf(tpi2).subtract(BigDecimal.valueOf(tpi1));
          int i = j.compareTo(BigDecimal.ZERO);
          if (i > 0) {
            return 1;
          } else if (i < 0) {
            return -1;
          } else {
            return 0;
          }
        }
      });
    }
    if (list.size() > 5) {
      list = list.subList(0, 5);
    }
    return list;
  }

  /**
   * .
   */
  public static double calPicTpi(List<RoadSectionInfoDto> list, StatementParam param) {
    double tpi = 0.00;
    for (RoadSectionInfoDto po : list) {
      tpi = tpi + po.getTpi();
    }
    tpi = TpiUtils.getByDigit(tpi / list.size(), param.getTpiDigit());
    return tpi;
  }

  /**
   * .
   */
  public static double calPicSpeed(List<RoadSectionInfoDto> list, StatementParam param) {
    double speed = 0.00;
    for (RoadSectionInfoDto po : list) {
      speed = speed + po.getSpeed();
    }
    speed = TpiUtils.getByDigit(speed / list.size(), param.getSpeedDigit());
    return speed;
  }

  /**
   * .
   */
  public static List<RoadSectionChangePo> getRoadSectionChangePoList(
      List<RoadSectionInfoDto> list) {
    List<RoadSectionChangePo> data = new ArrayList<>();
    int index = 1;
    for (RoadSectionInfoDto dto : list) {
      RoadSectionChangePo po = new RoadSectionChangePo();
      BeanUtils.copyProperties(dto, po);
      po.setIndex(index++);
      data.add(po);
    }
    return data;
  }

  /**
   * 根据环比得到变化情况.
   */
  public static String getSituation(double ratio) {
    if (ratio > 0) {
      return "恶化";
    } else if (ratio < 0) {
      return "改善";
    } else {
      return "不变";
    }
  }

  /**
   * 计算上期到这期的变化比例及变化情况.
   */
  public static List<RoadSectionChangeInfoPo> getCalResult(List<RoadSectionInfoDto> lastList,
      List<RoadSectionInfoDto> thisList, StatementParam param) {
    List<RoadSectionChangeInfoPo> data = new ArrayList<>();
    for (RoadSectionInfoDto thisDto : thisList) {
      double thisSpeed = thisDto.getSpeed();
      int typeId = thisDto.getTypeId();
      double thisTpi = TpiUtils.speedToTpi(thisSpeed, typeId);
      int thisId = thisDto.getId();
      for (RoadSectionInfoDto lastDto : lastList) {
        int lastId = lastDto.getId();
        if (thisId == lastId) {
          RoadSectionChangeInfoPo po = new RoadSectionChangeInfoPo();
          BeanUtils.copyProperties(lastDto, po);
          po.setSpeed(TpiUtils.getByDigit(thisSpeed, param.getSpeedDigit()));
          po.setTpi(TpiUtils.getByDigit(thisTpi, param.getTpiDigit()));
          double lastTpi = lastDto.getTpi();
          double ratio = TpiUtils.calculateGrowth(lastTpi, thisTpi, param.getRatioDigit());
          po.setRatio(ratio);
          po.setSituation(CalIndexUtils.getSituation(ratio));
          data.add(po);
        }
      }
    }
    return data;
  }

  /**
   * 计算改善条数.
   */
  public static int calTotalImprove(List<RoadSectionChangeInfoPo> list) {
    int count = 0;
    for (RoadSectionChangeInfoPo po : list) {
      double ratio = po.getRatio();
      if (ratio < 0) {
        count = count + 1;
      }
    }
    return count;
  }

  /**
   * 计算恶化条数.
   */
  public static int calTotalWorse(List<RoadSectionChangeInfoPo> list) {
    int count = 0;
    for (RoadSectionChangeInfoPo po : list) {
      double ratio = po.getRatio();
      if (ratio > 0) {
        count = count + 1;
      }
    }
    return count;
  }
}
