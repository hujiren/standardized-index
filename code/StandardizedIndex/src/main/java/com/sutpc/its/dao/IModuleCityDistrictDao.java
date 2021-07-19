package com.sutpc.its.dao;

import com.sutpc.its.po.CityDistrictNamePo;
import com.sutpc.its.po.CityDistrictSpeedPo;
import com.sutpc.its.po.CityDistrictTpiPo;
import com.sutpc.its.po.CityDistrictTpiRankPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 全市行政区交通运行模块仓库.
 *
 * @author admin
 * @date 2020/5/20 19:08
 */
@Mapper
@Repository
public interface IModuleCityDistrictDao {

  List<CityDistrictTpiRankPo> getTpiRank(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

  List<CityDistrictNamePo> getAllDistrict();

  List<CityDistrictTpiPo> getAllTpi(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

  List<CityDistrictSpeedPo> getAllSpeed(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

}
