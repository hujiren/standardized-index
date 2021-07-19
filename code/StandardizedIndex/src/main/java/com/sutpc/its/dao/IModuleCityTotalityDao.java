package com.sutpc.its.dao;

import com.sutpc.its.po.CityTotalitySpeedPo;
import com.sutpc.its.po.CityTotalityTpiPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 全市总体交通运行模块仓库.
 *
 * @author admin
 * @date 2020/5/20 19:08
 */
@Mapper
@Repository
public interface IModuleCityTotalityDao {

  double getAllAverageSpeed(@Param("startDate") int startDate, @Param("endDate") int endDate);

  List<CityTotalitySpeedPo> getAllSpeed(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

  List<CityTotalityTpiPo> getAllTpi(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

}
