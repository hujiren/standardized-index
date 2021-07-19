package com.sutpc.its.dao.v6;

import com.sutpc.its.po.v6.BikePointPo;
import com.sutpc.its.vo.StationSurroundRankingVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:51 2020/10/18.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IBikeDao {

  /**
   * 查询单车属性信息.
   */
  List<BikePointPo> getBikeInfo(@Param("time") int year, @Param("hour") int hour,
      @Param("lockStatus") int lockStatus);

  int getBikeCount(@Param("time") int year, @Param("hour") int hour,
      @Param("lockStatus") int lockStatus);

  int getBikeUseCount(@Param("time") int year, @Param("hour") int hour,
      @Param("lockStatus") int lockStatus);

  List<StationSurroundRankingVo> getSubwaySurroundAmount(@Param("time") int year,
      @Param("hour") int hour, @Param("lockStatus") int lockStatus);

  List<StationSurroundRankingVo> getBusSurroundAmount(@Param("time") int year,
      @Param("hour") int hour, @Param("lockStatus") int lockStatus);
}
