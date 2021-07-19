package com.sutpc.its.dao;

import com.sutpc.its.vo.CongestionWarnRealVo;
import com.sutpc.its.vo.RoadsectMapVolumeVo;
import com.sutpc.its.vo.SpeedAndTpiChartVo;
import com.sutpc.its.vo.VolumeChartVo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IMonitorEarlyWarningDao {

  List<Map<String, Object>> getBlockRealTimeTrafficOperationRanking(Map<String, Object> map);

  List<Map<String, Object>> getEveryBlockTpi(Map<String, Object> map);

  List<Map<String, Object>> getCityAndEveryDistrictTpi(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeTrafficOperationRanking(Map<String, Object> map);

  List<Map<String, Object>> getDistrictCarOrBusData(Map<String, Object> map);

  List<Map<String, Object>> getRealtimeBusOperationRanking(Map<String, Object> map);

  List<Map<String, Object>> getBuslaneRealTimeOperationStatus(Map<String, Object> map);

  List<Map<String, Object>> getSubsectRealTimeJamWarning(Map<String, Object> map);

  List<Map<String, Object>> getSpeedAndTpiInfoBySubsectfid(Map<String, Object> map);

  List<Map<String, Object>> getSubsectRealTimeTrafficStatus(Map<String, Object> map);

  List<Map<String, Object>> getPoiRealTimeJamWarning(Map<String, Object> map);

  List<Map<String, Object>> getSpeedAndTpiInfoByPoifid(Map<String, Object> map);

  List<Map<String, Object>> getPoiRealTimeTrafficStatus(Map<String, Object> map);

  List<Map<String, Object>> getBlockRealTimeJamWarning(Map<String, Object> map);

  List<Map<String, Object>> getSpeedAndTpiInfoByBlockfid(Map<String, Object> map);

  List<Map<String, Object>> getBlockRealTimeTrafficStatus(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectRealTimeJamWarning(Map<String, Object> map);

  List<Map<String, Object>> getSpeedAndTpiInfoByRoadsectfid(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectRealTimeTrafficStatus(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectRealTimeTrafficStatusPredict(Map<String, Object> map);

  List<Map<String, Object>> getPredictSpeedAndTpiInfoByRoadsectfid(Map<String, Object> map);

  List<Map<String, Object>> getRoadSectionFlowRanking(Map<String, Object> map);

  List<Map<String, Object>> getRoadSectionFlowsByLinkFid(Map<String, Object> map);

  List<Map<String, Object>> getCapacityByLinkFid(Map<String, Object> map);

  List<Map<String, Object>> getDcInfo(Map<String, Object> map);

  List<Map<String, Object>> getDetailInfoByLinkFid(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionDelayStatus(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionDelayDetails(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionDelayDayData(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionDelayDayDataRefill(Map<String, Object> map);

  List<Map<String, Object>> getHighSpeedRealTimeJamWarning(Map<String, Object> map);

  List<Map<String, Object>> getHighSpeedRealTimeTrafficStatus(Map<String, Object> map);

  List<Map<String, Object>> getHighSpeedLineDataByRoadsectfid(Map<String, Object> map);

  List<Map<String, Object>> getIntersectinDelayRanking(Map<String, Object> map);

  //断面流量新需求接口 start
  List<Map<String, Object>> getRoadSectFlowDistribution(Map<String, Object> map);

  List<Map<String, Object>> getDetectorDirFlowByDetectorFid(Map<String, Object> map);

  List<Map<String, Object>> getRoadSectionFlows(Map<String, Object> map);

  List<Map<String, Object>> getDetectorHourLineData(Map<String, Object> map);

  List<Map<String, Object>> getDetectorDirByFid(Map<String, Object> map);

  Map<String, Object> getCapacity(Map<String, Object> map);
  //断面流量新需求接口 end


  List<Map<String, Object>> getCongestionRoadNum(Map<String, Object> map);

  Map<String, Object> getCongestionRoadTotalLength(Map<String, Object> map);

  List<Map<String, Object>> getCongestionWarnReal(Map<String, Object> map);

  /**
   * 新-拥堵路段排名.
   */
  List<CongestionWarnRealVo> getCongestionWarnRealV2(@Param("time") String time,
      @Param("period15") int period15, @Param("config_district_fid") String[] config_district_fid);

  List<SpeedAndTpiChartVo> getSpeedAndTpiById(@Param("time") String time,
      @Param("period15") int period15, @Param("id") int roadsectId);

  List<VolumeChartVo> getVolumeById(@Param("time") String time, @Param("period15") int period15,
      @Param("id") int roadsectId);

  List<RoadsectMapVolumeVo> getMapRealVolume(@Param("time") String time,
      @Param("period15") int period15);

  List<Map<String, Object>> getCongestionWarnDetail(Map<String, Object> map);

  Map<String, Object> getCityIndex(Map<String, Object> map);

  List<Map<String, Object>> getGdHighSpeedLinkStatus(Map<String, Object> map);

  List<Map<String, Object>> getAllIntersectionInformation(Map<String, Object> map);
}
