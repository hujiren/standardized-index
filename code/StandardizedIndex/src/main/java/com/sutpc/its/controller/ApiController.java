package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.DetectorBaseInfoPo;
import com.sutpc.its.po.DetectorSpeedAndPcuPo;
import com.sutpc.its.po.DistrictDayTpiPo;
import com.sutpc.its.po.DistrictJamLengthPo;
import com.sutpc.its.po.PoiInfoNowPo;
import com.sutpc.its.po.RoadJamLengthPo;
import com.sutpc.its.po.RoadSectionInfoEntity;
import com.sutpc.its.po.RoadSectionSpeedByDayPo;
import com.sutpc.its.service.ApiService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * API接口.
 *
 * @Author: zuotw
 * @Date: created on 16:00 2019/12/31.
 * @Description
 * @Modified By:
 */
@Controller
@RequestMapping("openapi")
public class ApiController {

  @Autowired
  private ApiService apiService;

  /**
   * 实时道路运行监测信息.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRoadOperationMonitoringNow", method = RequestMethod.POST)
  public List<Map<String, Object>> getRoadOperationMonitoringNow(
      @RequestParam Map<String, Object> map) {
    return apiService.getRoadOperationMonitoringNow(map);
  }

  /**
   * 实时街道监测信息.
   */
  @ResponseBody
  @RequestMapping(value = "/GetBlockMonitoringNow", method = RequestMethod.POST)
  public List<Map<String, Object>> getBlockMonitoringNow(@RequestParam Map<String, Object> map) {
    return apiService.getBlockMonitoringNow(map);
  }

  /**
   * 关于全市工作日早晚高峰当月、当月同比、自年初累计、自年初累计同比、去年年均等平均车速统计结果.
   */
  @ResponseBody
  @RequestMapping(value = "/GetCitywideSpeedData", method = RequestMethod.POST)
  public Map<String, Object> getCitywideSpeedData(@RequestParam Map<String, Object> map) {
    return apiService.getCitywideSpeedData(map);
  }

  /**
   * 路段未来2小时预测速度 以小时为时间片.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRoadsectPredictStatusByHour")
  public List<Map<String, Object>> getRoadsectPredictStatusByHour(
      @RequestParam Map<String, Object> map) {
    return apiService.getRoadsectPredictStatusByHour(map);
  }

  /**
   * //返回当前的所有LINK的路况信息.
   */
  @ResponseBody
  @RequestMapping(value = "/GetLinkDataForTGis", method = RequestMethod.POST)
  public List<Map<String, Object>> getLinkDataForTGis(@RequestParam Map<String, Object> map) {
    return apiService.getLinkDataForTGis(map);
  }

  /**
   * old - 实时片区接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRealTimeSectStatus", method = RequestMethod.POST)
  public List<Map<String, Object>> getRealTimeSectStatus(@RequestParam Map<String, Object> map) {
    return apiService.getRealTimeSectStatus(map);
  }

  /**
   * 获取实时道路的指数   old - 实时道路接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRealTimeRoadConIndex", method = RequestMethod.POST)
  public List<Map<String, Object>> getRealTimeRoadConIndex(@RequestParam Map<String, Object> map) {
    return apiService.getRealTimeRoadConIndex(map);
  }

  /**
   * //获取实时行政区的指数.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRealTimeTotalConIndex", method = RequestMethod.POST)
  public List<Map<String, Object>> getRealTimeTotalConIndex(@RequestParam Map<String, Object> map) {
    return apiService.getRealTimeTotalConIndex(map);
  }

  /**
   * 行政区实时指数/速度接口.
   */
  @ResponseBody
  @PostMapping(value = "/GetDistrictStatusNow")
  public List<Map<String, Object>> getDistrictStatusNow(@RequestParam Map<String, Object> map) {
    return apiService.getDistrictStatusNow(map);
  }

  /**
   * 行政区历史指数查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetDistrictExptHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getDistrictExptHis(@RequestParam Map<String, Object> map) {
    return apiService.getDistrictExptHis(map);
  }

  /**
   * 行政区历史速度查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetDistrictSpeedHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getDistrictSpeedHis(@RequestParam Map<String, Object> map) {
    return apiService.getDistrictSpeedHis(map);
  }

  /**
   * 片区实时指数/速度接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetSubsectStatusNow", method = RequestMethod.POST)
  public List<Map<String, Object>> getSubsectStatusNow(@RequestParam Map<String, Object> map) {
    return apiService.getSubsectStatusNow(map);
  }

  /**
   * 片区历史指数/速度查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetSubsectStatusHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getSubsectStatusHis(@RequestParam Map<String, Object> map) {
    return apiService.getSubsectStatusHis(map);
  }

  /**
   * 道路实时指数/速度接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRoadStatusNow", method = RequestMethod.POST)
  public List<Map<String, Object>> getRoadStatusNow(@RequestParam Map<String, Object> map) {
    return apiService.getRoadStatusNow(map);
  }

  /**
   * 道路历史速度查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRoadStatusHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getRoadStatusHis(@RequestParam Map<String, Object> map) {
    return apiService.getRoadStatusHis(map);
  }

  /**
   * 路段未来2小时预测速度(5分钟时间片）.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRoadsectPredictStatus", method = RequestMethod.POST)
  public List<Map<String, Object>> getRoadsectPredictStatus(@RequestParam Map<String, Object> map) {
    return apiService.getRoadsectPredictStatus(map);
  }

  /**
   * 行政区实时公交速度接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetDistrictBusStatusNow", method = RequestMethod.POST)
  public List<Map<String, Object>> getDistrictBusStatusNow(@RequestParam Map<String, Object> map) {
    return apiService.getDistrictBusStatusNow(map);
  }

  /**
   * 行政区历史公交速度查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetDistrictBusCarStatusHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getDistrictBusCarStatusHis(
      @RequestParam Map<String, Object> map) {
    return apiService.getDistrictBusCarStatusHis(map);
  }

  /**
   * 片区实时公交速度接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetSubsectBusStatusNow", method = RequestMethod.POST)
  public List<Map<String, Object>> getSubsectBusStatusNow(@RequestParam Map<String, Object> map) {
    return apiService.getSubsectBusStatusNow(map);
  }

  /**
   * 片区历史公交速度查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetSubsectBusCarStatusHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getSubsectBusCarStatusHis(
      @RequestParam Map<String, Object> map) {
    return apiService.getSubsectBusCarStatusHis(map);
  }

  /**
   * 专用道历史公交速度查询接口.
   */
  @ResponseBody
  @RequestMapping(value = "/GetBusLaneBusCarStatusHis", method = RequestMethod.POST)
  public List<Map<String, Object>> getBusLaneBusCarStatusHis(
      @RequestParam Map<String, Object> map) {
    return apiService.getBusLaneBusCarStatusHis(map);
  }

  /**
   * 中路段实时速度.
   */
  @ResponseBody
  @RequestMapping(value = "/GetRealTimeRoadSectMidStatus", method = RequestMethod.POST)
  public List<Map<String, Object>> getRealTimeRoadSectMidStatus(
      @RequestParam Map<String, Object> map) {
    return apiService.getRealTimeRoadSectMidStatus(map);
  }

  /**
   * 获取行政区指定日期曲线，按月统计的指数和速度.
   */
  @ResponseBody
  @PostMapping(value = "/GetDistrictTpiAndSpeedGroupByMonth")
  public List<Map<String, Object>> getDistrictTpiAndSpeedGroupByMonth(
      @RequestParam Map<String, Object> map) {
    return apiService.getDistrictTpiAndSpeedGroupByMonth(map);
  }

  /**
   * 获取行政区指定日期曲线，按年统计的指数和速度.
   */
  @ResponseBody
  @PostMapping(value = "/GetDistrictTpiAndSpeedGroupByYear")
  public List<Map<String, Object>> getDistrictTpiAndSpeedGroupByYear(
      @RequestParam Map<String, Object> map) {
    return apiService.getDistrictTpiAndSpeedGroupByYear(map);
  }

  /**
   * 获取街道指定日期曲线，按月统计的指数和速度.
   */
  @ResponseBody
  @PostMapping(value = "/GetBlokcTpiAndSpeedGroupByMonth")
  public List<Map<String, Object>> getBlokcTpiAndSpeedGroupByMonth(
      @RequestParam Map<String, Object> map) {
    return apiService.getBlokcTpiAndSpeedGroupByMonth(map);
  }

  /**
   * 获取街道指定日期曲线，按年统计的指数和速度.
   */
  @ResponseBody
  @PostMapping(value = "/GetBlokcTpiAndSpeedGroupByYear")
  public List<Map<String, Object>> getBlokcTpiAndSpeedGroupByYear(
      @RequestParam Map<String, Object> map) {
    return apiService.getBlokcTpiAndSpeedGroupByYear(map);
  }

  /**
   * 获取道路指定日期曲线，按月统计的指数和速度.
   */
  @ResponseBody
  @PostMapping(value = "/GetRoadTpiAndSpeedGroupByMonth")
  public List<Map<String, Object>> getRoadTpiAndSpeedGroupByMonth(
      @RequestParam Map<String, Object> map) {
    return apiService.getRoadTpiAndSpeedGroupByMonth(map);
  }

  /**
   * 获取道路指定日期曲线，按年统计的指数和速度.
   */
  @ResponseBody
  @PostMapping(value = "/GetRoadTpiAndSpeedGroupByYear")
  public List<Map<String, Object>> getRoadTpiAndSpeedGroupByYear(
      @RequestParam Map<String, Object> map) {
    return apiService.getRoadTpiAndSpeedGroupByYear(map);
  }

  /**
   * 获取交叉口延误数据按月统计.
   */
  @ResponseBody
  @PostMapping(value = "/GetIntersectionDelayDataByMonth")
  public List<Map<String, Object>> getIntersectionDelayDataByMonth(
      @RequestParam Map<String, Object> map) {
    return apiService.getIntersectionDelayDataByMonth(map);
  }

  /**
   * 获取交叉口信息.
   */
  @ResponseBody
  @PostMapping(value = "/GetIntersectionInfo")
  public List<Map<String, Object>> getIntersectionInfo(@RequestParam Map<String, Object> map) {
    return apiService.getIntersectionInfo(map);
  }

  /**
   * 全市公交专用道实时速度状态.
   */
  @ResponseBody
  @PostMapping(value = "/GetRealTimeBusLaneSpeed")
  public List<Map<String, Object>> getRealTimeBusLaneSpeed(@RequestParam Map<String, Object> map) {
    return apiService.getRealTimeBusLaneSpeed(map);
  }

  /**
   * 行政区单日指数曲线数据.
   */
  @ResponseBody
  @PostMapping(value = "/GetDistrictDayTpi")
  public List<DistrictDayTpiPo> getDistrictDayTpi(@RequestParam Map<String, Object> map) {
    return apiService.getDistrictDayTpi(map);
  }


  /**
   * 行政区拥堵长度.
   */
  @ResponseBody
  @PostMapping(value = "/GetDistrictJamLength")
  public List<DistrictJamLengthPo> getDistrictJamLength(@RequestParam Map<String, Object> map) {
    return apiService.getDistrictJamLength(map);
  }

  /**
   * 全市实时热点运行状态.
   */
  @ResponseBody
  @PostMapping(value = "/GetPoiInfoNow")
  public List<PoiInfoNowPo> getPoiInfoNow(@RequestParam Map<String, Object> map) {
    return apiService.getPoiInfoNow(map);
  }

  @ResponseBody
  @PostMapping(value = "/GetRoadJamLength")
  public List<RoadJamLengthPo> getRoadJamLength(@RequestParam Map<String, Object> map) {
    return apiService.getRoadJamLength(map);
  }

  @ResponseBody
  @PostMapping(value = "/GetRoadSectionInfo")
  public HttpResult<List<RoadSectionInfoEntity>> getRoadSectionInfo() {
    return HttpResult.ok(apiService.getRoadSectionInfo());
  }

  @ResponseBody
  @PostMapping(value = "/GetRoadSectionInfoBySevenDay")
  public HttpResult<List<RoadSectionSpeedByDayPo>> getRoadSectionInfoBySevenDay(
      @RequestParam("yearMonth") int yearMonth, @RequestParam("id") int id) {
    List<RoadSectionSpeedByDayPo> list = apiService.getRoadSectionInfoBySevenDay(yearMonth, id);
    return HttpResult.ok(list);
  }

  @PostMapping(value = "GetAllDetectorInfo")
  @ResponseBody
  public HttpResult<List<DetectorBaseInfoPo>> getAllDetectorInfo() {
    return HttpResult.ok(apiService.getAllDetectorInfo());
  }

  @PostMapping(value = "GetDetectorSpeedAndPcuPo")
  @ResponseBody
  public HttpResult<List<DetectorSpeedAndPcuPo>> getDetectorSpeedAndPcuPo(
      @RequestParam("yearMonth") int yearMonth, @RequestParam("id") String id) {
    return HttpResult.ok(apiService.getDetectorSpeedAndPcuPo(yearMonth, id));
  }
}
