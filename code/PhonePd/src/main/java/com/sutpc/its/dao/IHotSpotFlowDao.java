package com.sutpc.its.dao;

import com.sutpc.its.dto.FlowsDistributionDto;
import com.sutpc.its.dto.KeyAreaInfoDto;
import com.sutpc.its.dto.KeyAreaLikeDto;
import com.sutpc.its.dto.PfChangeDto;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IHotSpotFlowDao {

  List<Map<String, Object>> getHotSpotFlow(Map<String, Object> map);

  List<Map<String, Object>> getHotspotFlowLineData(Map<String, Object> map);

  Map<String, Object> getHotspotFlowStatus(Map<String, Object> map);

  List<KeyAreaInfoDto> getKeyAreaInfo(@Param("time") int time, @Param("period15") int period15);

  List<PfChangeDto> getPfChangeChartData(@Param("time") int time, @Param("tazid") int tazid);

  PfChangeDto getPresentPf(@Param("time") int time, @Param("period15") int period15,
      @Param("tazid") int tazid);

  List<KeyAreaLikeDto> getKeyAreaInfoLikeName(@Param("name") String name);

  List<FlowsDistributionDto> getFlowsGroupByStatus(@Param("time") String time,
      @Param("period15") int period15);

  List<Map<String, Object>> selectHotspotPassFlow();
}
