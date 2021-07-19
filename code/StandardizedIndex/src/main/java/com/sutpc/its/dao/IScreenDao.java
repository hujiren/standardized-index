package com.sutpc.its.dao;

import com.sutpc.its.vo.OldScreenAllRoadMidSectVo;
import com.sutpc.its.vo.OldScreenDistrictChartVo;
import com.sutpc.its.vo.OldScreenDistrictSpeedRatioVo;
import com.sutpc.its.vo.RoadJamVo;
import com.sutpc.its.vo.RoadStatusInfoVo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IScreenDao {

  Map<String, Object> getRealTimeDistrictSpeedAndTpi(Map<String, Object> map);

  Map<String, Object> getLateseTime();

  List<Map<String, Object>> getKeyAreaData(Map<String, Object> map);

  List<RoadJamVo> getRealTimeJamRoad(@Param("time") String time, @Param("period") int period15);

  List<OldScreenDistrictChartVo> getAllAndDistrictExponentComparisonData(@Param("time") String time,
      @Param("period") int period15, @Param("config_district_fid") String[] districtFid);

  List<OldScreenAllRoadMidSectVo> getAllRoadMidSectExp(@Param("time") String time,
      @Param("period") int period);

  OldScreenDistrictSpeedRatioVo getDistrictCarAndBusRatio(@Param("time") String time,
      @Param("period") int period);

  List<RoadStatusInfoVo> getRoadStatus(@Param("time") String time, @Param("period") int period);

}
