package com.sutpc.its.service.v6;

import com.sutpc.its.po.v6.BikePointPo;
import com.sutpc.its.po.v6.BikeStationInfoPo;
import com.sutpc.its.vo.SlowBikeOperationVo;
import com.sutpc.its.vo.StationSurroundRankingVo;
import java.util.List;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:49 2020/10/18.
 * @Description
 * @Modified By:
 */
public interface IBikeService {

  /**
   * 站台周围单车点位信息.
   *
   * @param time 日期
   * @param hour 时刻
   * @param lockStatus 开关锁状态
   */
  List<BikePointPo> getBikeStationInfo(int time, int hour, int lockStatus);

  /**
   * 慢行单车运行情况.
   */
  SlowBikeOperationVo getSlowBikeOperationInfo(int time, int hour, int lockStatus);

  /**
   * 地铁周边接驳量排名.
   *
   * @param time 日期
   * @param hour 时刻
   * @param lockStatus 开关锁状态
   */
  List<StationSurroundRankingVo> getSubwaySurroundAmount(int time, int hour, int lockStatus);

  /**
   * 地铁周边接驳量排名.
   *
   * @param time 日期
   * @param hour 时刻
   * @param lockStatus 开关锁状态
   */
  List<StationSurroundRankingVo> getBusSurroundAmount(int time, int hour, int lockStatus);
}
