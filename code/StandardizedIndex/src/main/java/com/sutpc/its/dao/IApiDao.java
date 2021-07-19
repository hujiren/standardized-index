package com.sutpc.its.dao;

import com.sutpc.its.po.DistrictDayTpiPo;
import com.sutpc.its.po.DistrictJamLengthPo;
import com.sutpc.its.po.PoiInfoNowPo;
import com.sutpc.its.po.RoadJamLengthPo;
import com.sutpc.its.po.RoadSectionInfoEntity;
import com.sutpc.its.po.RoadSectionSpeedByDayPo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:59 2019/12/31.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IApiDao {

  List<Map<String, Object>> getRoadOperationMonitoringNow(Map<String, Object> map);

  List<Map<String, Object>> getBlockMonitoringNow(Map<String, Object> map);

  Map<String, Object> getCitywideSpeedData(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectPredictStatusByHour(Map<String, Object> map);

  List<Map<String, Object>> getLinkDataForTGis(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeSectStatus(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeRoadConIndex(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeTotalConIndex(Map<String, Object> map);

  List<Map<String, Object>> getDistrictStatusNow(Map<String, Object> map);

  List<Map<String, Object>> getDistrictExptHis(Map<String, Object> map);

  List<Map<String, Object>> getDistrictSpeedHis(Map<String, Object> map);

  List<Map<String, Object>> getSubsectStatusNow(Map<String, Object> map);

  List<Map<String, Object>> getSubsectStatusHis(Map<String, Object> map);

  List<Map<String, Object>> getRoadStatusNow(Map<String, Object> map);

  List<Map<String, Object>> getRoadStatusHis(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectPredictStatus(Map<String, Object> map);

  List<Map<String, Object>> getDistrictBusStatusNow(Map<String, Object> map);

  List<Map<String, Object>> getDistrictBusCarStatusHis(Map<String, Object> map);

  List<Map<String, Object>> getSubsectBusStatusNow(Map<String, Object> map);

  List<Map<String, Object>> getSubsectBusCarStatusHis(Map<String, Object> map);

  List<Map<String, Object>> getBusLaneBusCarStatusHis(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeRoadSectMidStatus(Map<String, Object> map);

  List<Map<String, Object>> getDistrictTpiAndSpeedGroupByMonth(Map<String, Object> map);

  List<Map<String, Object>> getDistrictTpiAndSpeedGroupByYear(Map<String, Object> map);

  List<Map<String, Object>> getBlokcTpiAndSpeedGroupByMonth(Map<String, Object> map);

  List<Map<String, Object>> getBlokcTpiAndSpeedGroupByYear(Map<String, Object> map);

  List<Map<String, Object>> getRoadTpiAndSpeedGroupByMonth(Map<String, Object> map);

  List<Map<String, Object>> getRoadTpiAndSpeedGroupByYear(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionInfo(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionDelayDataByMonth(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeBusLaneSpeed(Map<String, Object> map);

  List<DistrictDayTpiPo> getDistrictDayTpi(Map<String, Object> map);

  List<DistrictJamLengthPo> getDistrictJamLength(Map<String, Object> map);

  List<RoadSectionInfoEntity> getRoadSectionInfo();

  List<RoadSectionSpeedByDayPo> getRoadSectionInfoBySevenDay(@Param("startDate") String startDate,
      @Param("endDate") String endDate, @Param("id") int id);

  List<PoiInfoNowPo> getPoiInfoNow(Map<String, Object> map);

  List<RoadJamLengthPo> getRoadJamLength(Map<String, Object> map);
}
