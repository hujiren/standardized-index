package com.sutpc.its.service.v6.impl;

import com.sutpc.its.dao.v6.IBikeDao;
import com.sutpc.its.po.v6.BikePointPo;
import com.sutpc.its.service.v6.IBikeService;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.vo.SlowBikeOperationVo;
import com.sutpc.its.vo.StationSurroundRankingVo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:50 2020/10/18.
 * @Description
 * @Modified By:
 */
@Service
@Slf4j
public class BikeServiceImpl implements IBikeService {

  @Value("classpath:bus_outside50m.json")
  private Resource busResource;
  @Value("classpath:subway_outside50m.json")
  private Resource subwayResource;

  @Autowired
  private IBikeDao bikeDao;

  /**
   * 站台周围单车点位信息.
   *
   * @param time 日期
   * @param hour 时刻
   * @param lockStatus 开关锁状态
   */
  @Override
  public List<BikePointPo> getBikeStationInfo(int time, int hour, int lockStatus) {
    List<BikePointPo> list = bikeDao.getBikeInfo(time, hour, lockStatus);
    return list;
  }

  /**
   * 慢行单车运行情况.
   */
  @Override
  public SlowBikeOperationVo getSlowBikeOperationInfo(int time, int hour, int lockStatus) {
    int bikeCount = bikeDao.getBikeCount(time, hour, lockStatus);
    int bikeUseCount = bikeDao.getBikeUseCount(time, hour, lockStatus);
    SlowBikeOperationVo vo = new SlowBikeOperationVo();
    vo.setCount(bikeCount);
    vo.setTravel(bikeUseCount);
    vo.setAverage(TpiUtils.getByDigit(Double.parseDouble(String.valueOf(bikeUseCount)) / Double
        .parseDouble(String.valueOf(bikeCount)), 2));
    return vo;
  }

  /**
   * 地铁周边接驳量排名.
   *
   * @param time 日期
   * @param hour 时刻
   * @param lockStatus 开关锁状态
   */
  @Override
  public List<StationSurroundRankingVo> getSubwaySurroundAmount(int time, int hour,
      int lockStatus) {
    List<StationSurroundRankingVo> list = bikeDao.getSubwaySurroundAmount(time, hour, lockStatus);
    //前端显示前五条
    if (list != null && list.size() > 5) {
      list = list.subList(0, 5);
    }
    return list;
  }

  /**
   * 地铁周边接驳量排名.
   *
   * @param time 日期
   * @param hour 时刻
   * @param lockStatus 开关锁状态
   */
  @Override
  public List<StationSurroundRankingVo> getBusSurroundAmount(int time, int hour, int lockStatus) {
    List<StationSurroundRankingVo> list = bikeDao.getSubwaySurroundAmount(time, hour, lockStatus);
    //前端显示前五条
    if (list != null && list.size() > 5) {
      list = list.subList(0, 5);
    }
    return list;
  }
}
