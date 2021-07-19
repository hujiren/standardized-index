package com.sutpc.its.dao.v6;

import com.sutpc.its.po.PeriodValuePo;
import com.sutpc.its.po.RegionHistoryTrendPo;
import com.sutpc.its.po.v6.RoadTypeLengthsPo;
import com.sutpc.its.po.v6.UserDefinedInfoPo;
import com.sutpc.its.vo.HistoryInfoVo;
import com.sutpc.its.vo.JamRoadSectionVo;
import com.sutpc.its.vo.RealTimeTotalIndexVo;
import com.sutpc.its.vo.RegionSpeedAndTpiVo;
import com.sutpc.its.vo.RelatedSectionVo;
import com.sutpc.its.vo.TotalRealTimeMonitorVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:31 2020/10/18.
 * @Description
 * @Modified By:
 */
@Mapper
@Repository
public interface IUserDefinedDao {


  int setDefinedInfo(@Param("id") String id, @Param("time") String time, @Param("name") String name,
      @Param("region") String region, @Param("area") double area, @Param("listStr") String listStr,
      @Param("points") String points, @Param("links") String links, @Param("roads") String roads,
      @Param("sections") String sections,
      @Param("lengths") double lengths, @Param("type") int type,
      @Param("creator") String creator);

  List<UserDefinedInfoPo> getDefinedBaseInfo();

  List<Integer> getRoadIdByLInks(@Param("links") String[] links);

  List<Integer> getRoadSectioinIdByLInks(@Param("links") String[] links);

  int updateDefinedInfo(@Param("name") String name, @Param("id") String id);

  int deleteDefinedInfoById(@Param("id") String id);

  int deleteHistoryInfoById(@Param("id") String id);

  List<RoadTypeLengthsPo> getRoadTypeLengths(@Param("links") String[] links);

  UserDefinedInfoPo getDefinedBaseInfo(@Param("id") String id);

  RegionSpeedAndTpiVo getRegionSpeedAndTpi(@Param("time") String time, @Param("period") int period,
      @Param("sections") String[] sections);

  RealTimeTotalIndexVo getRegionJamLengthAndRatio(@Param("time") String time,
      @Param("period") int period, @Param("roads") String[] roads);

  List<TotalRealTimeMonitorVo> getTotalRealTimeMonitor(@Param("time") String time,
      @Param("period") int period, @Param("sections") String[] sections);

  List<RelatedSectionVo> getRelatedSectionMonitor(@Param("time") String time,
      @Param("period") int period, @Param("sections") String[] sections);

  HistoryInfoVo getHistoryAllDayInfo(@Param("time") String time,
      @Param("sections") String[] sections);

  HistoryInfoVo getHistoryMorningPeakInfo(@Param("time") String time,
      @Param("sections") String[] sections);

  HistoryInfoVo getHistoryEveningPeakInfo(@Param("time") String time,
      @Param("sections") String[] sections);

  HistoryInfoVo getHistoryFlatHumpPeakInfo(@Param("time") String time,
      @Param("sections") String[] sections);

  List<JamRoadSectionVo> getJamRoadSection(@Param("time") String time, @Param("period") int period,
      @Param("sections") String[] sections);

  double getRoadJamLengthRatio(@Param("time") String time, @Param("period") int period,
      @Param("roads") String[] roads);

  double getRoadPeakTpi(@Param("time") String time, @Param("sections") String[] sections);

  List<PeriodValuePo> getSpeedList(@Param("time") String time,
      @Param("sections") String[] sections);

  double getAvgSpeed(@Param("time") String time, @Param("sections") String[] sections);

  int setHistoryInfo(RegionHistoryTrendPo po);

  List<RegionHistoryTrendPo> getHistroyInfo(@Param("id") String id,
      @Param("startTime") String startTime, @Param("endTime") String endTime);


}
