package com.sutpc.its.dao;

import com.sutpc.its.dto.RoadSectionInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交通恶化路段.
 *
 * @Author: zuotw
 * @Date: created on 15:37 2020/6/2.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IModuleDistrictImproveDao {

  /**
   * 中路段列表.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<RoadSectionInfoDto> getRoadSectionList(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);
}
