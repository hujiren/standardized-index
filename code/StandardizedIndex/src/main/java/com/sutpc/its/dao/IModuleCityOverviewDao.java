package com.sutpc.its.dao;

import com.sutpc.its.po.CityDistrictTpiRankPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 全市模块仓库.
 *
 * @author admin
 * @date 2020/5/20 19:08
 */
@Mapper
@Repository
public interface IModuleCityOverviewDao {

  double getAllAverageSpeed(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getAllAverageTpi(@Param("startDate") int startDate, @Param("endDate") int endDate);

  List<CityDistrictTpiRankPo> getDistrictTpiRank(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

  double getPrimaryForMorning(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getPrimaryForEvening(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getHubForMorning(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getHubForEvening(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getPortForMorning(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getPortForEvening(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getGatewayForMorning(@Param("startDate") int startDate, @Param("endDate") int endDate);

  double getGatewayForEvening(@Param("startDate") int startDate, @Param("endDate") int endDate);

}
