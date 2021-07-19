package com.sutpc.its.service.v6;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.v6.AreaInfoPo;
import com.sutpc.its.po.v6.UserDefinedInfoPo;
import com.sutpc.its.vo.HistorySituationVo;
import com.sutpc.its.vo.JamRoadSectionVo;
import com.sutpc.its.vo.RealTimeTotalIndexVo;
import com.sutpc.its.vo.RegionOperationCharacteristicsVo;
import com.sutpc.its.vo.RelatedSectionVo;
import com.sutpc.its.vo.TotalMonitor;
import java.util.List;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:29 2020/10/18.
 * @Description
 * @Modified By:
 */
public interface IUserDefinedService {

  /**
   * 存入选择信息.
   *
   * @param name 名称
   * @param region 组成区域
   * @param area 面积
   * @param points 经纬度范围
   * @param links 小路段id
   * @param type 类型
   * @param creator 创建人
   * @return 状态
   */
  HttpResult setDefinedInfo(String name, String region, double area, String points, String links,
      int type, String creator);

  /**
   * 获取点选信息.
   */
  List<UserDefinedInfoPo> getDefinedBaseInfo();

  /**
   * 修改名称.
   */
  HttpResult updateDefinedInfo(String name, String id);

  /**
   * 删除信息.
   */
  HttpResult deleteDefinedInfoById(String id);

  /**
   * 所选区域信息.
   */
  AreaInfoPo getAreaInfoPo(String id);

  /**
   * 区域实时总体指标.
   *
   * @param id 自定义区域id
   */
  RealTimeTotalIndexVo getRealTimeTotalIndex(String id);

  /**
   * 总体实时监测.
   */
  TotalMonitor getTotalRealTimeMonitor(String id);

  /**
   * 关联路段监测.
   */
  List<RelatedSectionVo> getRelatedSectionMonitor(String id);

  /**
   * 历史态势分析.
   */
  HistorySituationVo getHistorySituationInfo(String id);

  /**
   * 拥堵识别点.
   */
  List<JamRoadSectionVo> getJamRoadSection(String id);

  /**
   * 区域运行特征.
   */
  RegionOperationCharacteristicsVo getRegionOperationCharacteristics(String id);


}
