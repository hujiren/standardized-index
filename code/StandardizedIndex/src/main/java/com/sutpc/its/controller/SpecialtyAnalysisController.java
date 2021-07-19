package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.dto.RoadCharacteristicDto;
import com.sutpc.its.dto.RoadPortrayalTagDto;
import com.sutpc.its.dto.RoadSectionJamCountDto;
import com.sutpc.its.dto.RoadsectDistributionDto;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.model.RoadLevelDistributionEntity;
import com.sutpc.its.model.RoadPortraitEntity;
import com.sutpc.its.model.RoadPortrayalRoadsectEntity;
import com.sutpc.its.model.RoadPortrayalTagEntity;
import com.sutpc.its.model.StandardizedPageModel;
import com.sutpc.its.service.SpecialtyAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/specialtyAnalysis")
@Api(tags = "专业分析")
@Scanning
public class SpecialtyAnalysisController {

  @Autowired
  public SpecialtyAnalysisService specialtyAnalysisService;

  @ResponseBody
  @PostMapping(value = "getJamSpaceTimeData")
  @ApiOperation(value = "道路运行-拥堵时空图-15分钟", notes = "道路运行")
  @Requirement(value = "获取指定大路段指定日期范围内各中路段范围的全天的道路运行情况。以15分钟时间颗粒统计。")
  @Input(value = "路段id、开始日期、结束日期、日期特征")
  @Output(value = "大路段名称")
  @Handle(value = "后台会查询指定日期范围内指定大路段分布的中路段的每个时间粒度的指数值，然后装配成为指定的格式。结果集"
      + "：c1-指数8-10，c2-指数6-8，c3-指数4-6，c4-指数2-4，c5-指数0-2")
  @Parameter(value = "road_fid,startdate,enddate,dateproperty")
  public HttpResult<Object> getJamSpaceTimeData(@RequestParam Map<String, Object> paramap) {
    return HttpResult.ok(specialtyAnalysisService.getJamSpaceTimeData(paramap));
  }

  @ResponseBody
  @PostMapping(value = "getJamSpaceTimeFiveMinData")
  @ApiOperation(value = "道路运行-拥堵时空图-5分钟", notes = "道路运行")
  @Requirement(value = "获取指定大路段指定日期范围内各中路段范围的全天的道路运行情况。以5分钟时间颗粒统计。")
  @Input(value = "路段id、开始日期、结束日期、日期特征")
  @Output(value = "大路段名称")
  @Handle(value = "后台会查询指定日期范围内指定大路段分布的中路段的每个时间粒度的指数值，然后装配成为指定的格式。结果集"
      + "：c1-指数8-10，c2-指数6-8，c3-指数4-6，c4-指数2-4，c5-指数0-2")
  @Parameter(value = "road_fid,startdate,enddate,dateproperty")
  public HttpResult<Object> getJamSpaceTimeFiveMinData(@RequestParam Map<String, Object> paramap) {
    return HttpResult.ok(specialtyAnalysisService.getJamSpaceTimeFiveMinData(paramap));
  }

  @ResponseBody
  @PostMapping(value = "getRoadWeekChange")
  @ApiOperation(value = "道路运行-道路运行周变图", notes = "道路运行")
  @Requirement(value = "获取中路段指定周每天二十四小时按小时统计的运行情况。前端展示用7*24的排列，用运行情况对应的5中颜色来显示运行状态。")
  @Input(value = "中路段id、具体哪一周。")
  @Output(value = "w1-01-s1。其中：w1代表一周第一天，范围w1-w7。01代表第一个小时，范围01-24。s1代表状态，范围：s1-s5，其中s1代表畅通，s2基本畅通，s3缓行，s4较拥堵，s5拥堵。")
  @Handle(value = "后台会把中路段7天24小时的运行状态数据，通过循环排列，转换为w1-01-s1的形式，提供给前端调用展示。")
  @Parameter(value = "roadsect_fid,week_no")
  public HttpResult<Object> getRoadWeekChange(@RequestParam Map<String, Object> paramap) {
    return HttpResult.ok(specialtyAnalysisService.getRoadWeekChange(paramap));
  }

  @ResponseBody
  @PostMapping(value = "getRoadWeekCompare")
  @ApiOperation(value = "道路运行-空间剖面图", notes = "道路运行")
  @Requirement(value = "获取指定日期范围、指定时段范围、指定日期特征、指定道路的平均速度，分中路段展示。")
  @Input(value = "大路段id、开始日期、结束日期、日期特征、开始时间、结束时间、时段范围")
  @Output(value = "中路段名称、中路段起点、中路段终点、方向、速度")
  @Handle(value = "后台会把开始时间和结束时间以及时段范围等，转换成查询需要的条件时间片，然后查询出来再组装成现有的格式。")
  @Parameter(value = "road_fid,startdate,enddate,dateproperty,starttime,endtime,timeproperty")
  public HttpResult<Object> getRoadWeekCompare(@RequestParam Map<String, Object> paramap) {
    return HttpResult.ok(specialtyAnalysisService.getRoadWeekCompare(paramap));
  }

  @ResponseBody
  @PostMapping(value = "getJamRoadsectRanking")
  @ApiOperation(value = "道路运行-路段排名/拥堵路段分析", notes = "道路运行")
  @Requirement(value = "获取指定行政区或指定几个行政区内的道路指定时间日期范围、时段范围、日期特性等的交通速度和指数信息。")
  @Input(value = "查询指标、是否工作日、开始日期、结束日期、开始时间、结束时间、时段范围、id")
  @Output(value = "速度、指数、中路段名称、方向、起点、终点 、中路段id、行政区名称")
  @Handle(value = "后台会把开始时间和结束时间以及时段范围等，转换成查询需要的条件时间片，然后查询出来再组装成现有的格式。")
  @Parameter(value = "index,dateproperty,roadsect_fname,district_fname,roadsect_fid,road_type_fid,speed,tpi")
  public HttpResult<Object> getJamRoadsectRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getJamRoadsectRanking(map));
  }

  @ResponseBody
  @PostMapping(value = "getParkingTollRoadData")
  @ApiOperation(value = "道路运行-停车收费路段", notes = "道路运行")
  @Requirement(value = "获取指定行政区内指定时间内条件内的平均速度。同时还要获取指定时间内的道路的平均速度，并在地图上标注出来。")
  @Input(value = "查询类型、日期特征、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围")
  @Output(value = "行政区速度、行政区名称、道路名称、道路id、道路速度")
  @Handle(value = "")
  @Parameter(value = "speed，road_id，road_name")
  public HttpResult<Object> getParkingTollRoadData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getParkingTollRoadData(map));
  }

  @ResponseBody
  @PostMapping(value = "getHolidayInitData")
  @ApiOperation(value = "假日评估-获取节假日页面初始化数据", notes = "假日评估")
  @Requirement(value = "获取节假日页面初始化的基础数据，包含了：所有热点信息（经纬度信息，fid），节假日信息（节假日id，名称），热点类型")
  @Input(value = "")
  @Output(value = "热点集合、节假日信息集合、热点类型集合")
  @Handle(value = "分别查询出三种集合再组装在一起。")
  @Parameter
  public HttpResult<Object> getHolidayInitData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getHolidayInitData(map));
  }

  @ResponseBody
  @PostMapping(value = "getHolidayQueryResult")
  @ApiOperation(value = "假日评估-获取节假日查询结果", notes = "假日评估")
  @Requirement(value = "获取指定节假日的热点运行情况及热点周边路段运行情况。")
  @Input(value = "年份、节假日id、热点类型、时间范围、道路类型")
  @Output(value = "拥堵时长、路段名称、路段起点、路段终点、拥堵时间、方向、拥堵速度")
  @Handle(value = "后台会把拿到的id转换为数据库查询需要的数据格式，查询出结果，并转换成前端需要的数据格式。")
  @Parameter(value = "year，holiday_fid，spot_type，stage，road_type_fid")
  public HttpResult<Object> getHolidayQueryResult(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getHolidayQueryResult(map));
  }


  @ResponseBody
  @PostMapping(value = "getSpotAroundJamRoadsectStatus")
  @ApiOperation(value = "假日评估-热点周围中路段拥堵情况", notes = "假日评估")
  @Requirement(value = "获取指定节假日热点周围中路段的拥堵情况。")
  @Input(value = "年份、节假日id、热点类型、时间范围、道路类型")
  @Output(value = "中路段id、指数、热点id、速度、热点类型、运行状态")
  @Handle(value = "后台会把拿到的id转换为数据库查询需要的数据格式，查询出结果，并转换成前端需要的数据格式。")
  @Parameter(value = "year，holiday_fid，spot_type，stage，road_type_fid")
  public HttpResult<Object> getSpotAroundJamRoadsectStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getSpotAroundJamRoadsectStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getTaxiIndexData")
  @ApiOperation(value = "获取出租车指标数据", notes = "车辆运行-出租车")
  @Requirement(value = "获取指定时间段、日期特征、时段范围的出租车指标数。并按指定的时间颗粒出结果。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "车均有效运营次数（AVG_TRIP_NUM）、车均有效运营时间（VEHICLE_TRIP_TIME）、时间空载率（VALID_TIME_RATE）、车均有效运营里程（VEHICLE_TRIP_DIS）、总运营次数（TRIP_NUM）、里程空载率（VALID_DIS_RATE）")
  @Handle(value = "后台会添加一个小时实时的参数")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type")
  public HttpResult<Object> getTaxiIndexData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getTaxiIndexData(map));
  }

  @ResponseBody
  @PostMapping(value = "getDistributionInfo")
  @ApiOperation(value = "车辆运行-分布数据（速度，距离，时间）", notes = "车辆运行")
  @Requirement(value = "获取分布数据信息，包含速度、距离、时间。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "距离合集、速度合集、时间合集")
  @Handle(value = "后台分别查询出距离合集、速度合集、时间合集，然后统一放入一个对象返回给前端。")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type")
  public HttpResult<Object> getDistribution(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getDistribution(map));
  }

  @ResponseBody
  @PostMapping(value = "getOdDistributionData")
  @ApiOperation(value = "车辆运行-od分布数据", notes = "车辆运行")
  @Requirement(value = "获取选取点到其他各点的指定日期范围条件内的分布情况。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型、选取点id")
  @Output(value = "时间、日期、流量")
  @Handle(value = "")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type，fid。http://wiki.sutpc.org/pages/viewpage.action?pageId=25471796")
  public HttpResult<Object> getOdDistributionData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getOdDistributionData(map));
  }

  @ResponseBody
  @PostMapping(value = "getPoiTripData")
  @ApiOperation(value = "车辆运行-热点出行分布", notes = "车辆运行")
  @Requirement(value = "获取选取点到其他各点的指定日期范围条件内的分布情况。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型、选取点id")
  @Output(value = "时间、日期、流量")
  @Handle(value = "")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type，fid。http://wiki.sutpc.org/pages/viewpage.action?pageId=25486361")
  public HttpResult<Object> getPoiTripData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getPoiTripData(map));
  }

  @ResponseBody
  @PostMapping(value = "getTripsData")
  @ApiOperation(value = "车辆运行-出行量", notes = "车辆运行")
  @Requirement(value = "获取车辆运行的出行量信息。包含了fid、时间、出发量和到达量。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "时间、日期、出发量、到达量")
  @Handle(value = "")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type，详细：http://wiki.sutpc.org/pages/viewpage.action?pageId=25473031")
  public HttpResult<Object> getTripsData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getTripsData(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadsectFlow")
  @ApiOperation(value = "车辆运行-通道流量", notes = "车辆运行")
  @Requirement(value = "根据车辆类型以及其他日期范围等查询条件，查询出对应的通道流量，并展示在地图。")
  @Input(value = "开始日期、结束日期、车辆类型、时间颗粒、日期类型、小时")
  @Output(value = "道路名（fname）、起点（roadsect_from）、终点（roadsect_to）、时间/日期（time）、中路段id")
  @Handle(value = "")
  @Parameter(value = "startdate,enddate,vehicle_type,timeprecision,date,hh")
  public HttpResult<Object> getRoadsectFlow(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getRoadsectFlow(map));
  }

  @ResponseBody
  @PostMapping(value = "getDistrictLeaveAndArrivalRanking")
  @ApiOperation(value = "车辆运行-客货运-行政区排名", notes = "车辆运行")
  @Requirement(value = "获取行政区出行量排名信息。")
  @Input(value = "车辆类型、开始日期、结束日期、日期特征、时段")
  @Output(value = "到达量数据包（arrival）、离开量数据包（leave）、行政区id（fid）、行政区名称（fname）、出发量/到达量")
  @Handle(value = "")
  @Parameter(value = "vehicle_type，startdate，enddate，dateproperty，timeproperty")
  public HttpResult<Object> getDistrictLeaveAndArrivalRanking(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getDistrictLeaveAndArrivalRanking(map));
  }

  @ResponseBody
  @PostMapping(value = "getNavigationCarIndexData")
  @ApiOperation(value = "车辆运行-导航小汽车指标", notes = "车辆运行")
  @Requirement(value = "获取导航小汽车指标信息。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "出行时间（trip_time）、距离（trip_dis）、速度（trip_speed)")
  @Handle
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type")
  public HttpResult<Object> getNavigationCarIndexData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getNavigationCarIndexData(map));
  }

  @ResponseBody
  @PostMapping(value = "getContrastiveData")
  @ApiOperation(value = "对比分析", notes = "对比分析")
  @Requirement(value = "")
  @Input(value = "")
  @Output(value = "")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> getContrastiveData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getContrastiveData(map));
  }

  @ResponseBody
  @PostMapping(value = "getTotalData")
  @ApiOperation(value = "对比分析")
  @Requirement(value = "")
  @Input(value = "")
  @Output(value = "")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> getTotalData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getTotalData(map));
  }


  /**
   * 获取道路运行状态饼状图信息.
   *
   * @param map 为空
   * @author chensy
   */
  @ResponseBody
  @PostMapping(value = "getRoadRunState")
  @ApiOperation(value = "道路画像（旧）-道路特征-道路运行状态", notes = "道路画像/道路特征")
  @Requirement(value = "获取道路运行状态信息。")
  @Input(value = "无")
  @Output(value = "畅通百分比、基本畅通百分比、缓行百分比、较拥堵百分比、拥堵百分比")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> getRoadRunState(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getRoadRunState(map));
  }


  /**
   * 获取道路等级数量.
   *
   * @param map 为空
   */
  @ResponseBody
  @PostMapping(value = "getRoadFlevelNum")
  @ApiOperation(value = "道路画像（旧）-道路等级数量", notes = "道路画像/道路特征")
  @Requirement(value = "获取道路等级数量。")
  @Input(value = "")
  @Output(value = "道路等级id")
  @Handle(value = "数量")
  @Parameter(value = "道路等级名称")
  public HttpResult<Object> getRoadFlevelNum(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getRoadFlevelNum(map));
  }


  /**
   * 获取拥堵路段列表.
   *
   * @param map startdate,enddate,timeproperty,period,dateproperty
   */
  @ResponseBody
  @PostMapping(value = "getCongestionRoadInfo")
  @ApiOperation(value = "道路画像（旧）-道路特征池-拥堵路段列表", notes = "道路画像/道路特征")
  @Requirement(value = "获取拥堵路段列表。现已经集成道路等数量和道路运行状态两个接口。")
  @Input(value = "日期特征、开始日期、结束日期、时段范围、开始时间、结束时间、页码、页面尺寸、道路类型、路段类型、行政区id")
  @Output(value = "拥堵道路列表信息、最后一页页码、下一页页码、当前页页码、前一页页码、第一页页码、总页数、总记录数、列表信息、"
      + "路段名称、路段起点、路段终点、速度、指数、是否公交专用道、是否易拥堵、是否多发事故路段、是否易发积水、是否停车收费路段、"
      + "拥堵时长、路段施工状态、公交速度比、拥堵时空值、拥堵里程")
  @Handle(value = "后端会根据页面的各部分信息，组装查询数据，并把查询后的数据按照既定格式返回。")
  @Parameter(value = "dateproperty，startdate，enddate，timeproperty，starttime，endtime，pageNum，pageSize，laneType，roadsectAttribute，district_fid")
  public /*Map<String,Object>*/HttpResult<Object> getCongestionRoadInfo(
      @RequestParam Map<String, Object> map) {

    //获取分页列表数据
    StandardizedPageModel pm = specialtyAnalysisService.getCongestionRoadInfo(map);

    //获取道路运行状态
    Map<String, Object> roadRunStateMap = specialtyAnalysisService.getRoadRunState(map);

    //获取道路等级数量
    List<Map<String, Object>> roadFlevelList = specialtyAnalysisService.getRoadFlevelNum(map);

    Map<String, Object> resultMap = new HashMap<String, Object>();

    resultMap.put("congestionRoadInfo", pm);
    resultMap.put("roadRunStateInfo", roadRunStateMap);
    resultMap.put("roadFlevelInfo", roadFlevelList);

    return HttpResult.ok(resultMap);
  }


  @ResponseBody
  @PostMapping(value = "getCongestionRoadInfoByDistrict")
  @ApiOperation(value = "道路画像（旧）-场景应用-通过区域获取拥堵路段信息", notes = "道路画像/场景应用")
  @Requirement(value = "获取指定行政区拥堵路段信息，并在地图中标注相关信息。")
  @Input(value = "行政区id、开始日期、结束日期、开始时间、结束时间、时段范围")
  @Output(value = "拥堵路段信息、路段id、路段名称、路段起点、路段终点、速度、指数、拥堵路段信息、热点区域id、热点区域名称、经度、纬度")
  @Handle(value = "")
  @Parameter(value = "dateproperty，startdate，enddate，timeproperty，starttime，endtime，district_fid")
  public HttpResult<Object> getCongestionRoadInfoByDistrict(@RequestParam Map<String, Object> map) {

    return HttpResult.ok(specialtyAnalysisService.getCongestionRoadInfoByDistrict(map));
  }


  /**
   * 获取货运车辆出发量/到达量接口.
   *
   * @param map startdate,enddate,timeproperty,vehicleType,dateproperty,check_type,timeprecision
   */
  @ResponseBody
  @PostMapping(value = "getArrivalLeaveFreightTransportNum")
  @ApiOperation(value = "货运评估-货运车辆出发量/到达量", notes = "货运车辆/运行特征")
  @Requirement(value = "获取货运车辆的出发量和到达量。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "时间、日期、出发量、到达量")
  @Handle(value = "")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type")
  public HttpResult<Object> getArrivalLeaveFreightTransportNum(
      @RequestParam Map<String, Object> map) {

    return HttpResult.ok(specialtyAnalysisService.getArrivalLeaveFreightTransportNum(map));
  }

  /**
   * 获取货运车辆出发量/到达量分布信息.
   *
   * @param map startdate,enddate,timeproperty,vehicleType,dateproperty,check_type,timeprecision
   */
  @ResponseBody
  @PostMapping(value = "getArrivalLeaveFreightTransportDistribution")
  @ApiOperation(value = "货运评估-货运车辆出发量/到达量分布信息", notes = "货运车辆/运行特征")
  @Requirement(value = "获取货运车辆的出发量和到达量。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "时间、日期、出发量、到达量")
  @Handle(value = "")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type")
  public HttpResult<Object> getArrivalLeaveFreightTransportDistribution(
      @RequestParam Map<String, Object> map) {

    return HttpResult.ok(specialtyAnalysisService.getArrivalLeaveFreightTransportDistribution(map));
  }

  /**
   * 获取货运车辆热点区域出发量/到达量.
   *
   * @param map startdate,enddate,timeproperty,vehicle_type,dateproperty,timeprecision,fid
   */
  @ResponseBody
  @PostMapping(value = "getFreightTransportHpArrivalLeave")
  @ApiOperation(value = "货运评估-货运车辆热点区域出发量/到达量", notes = "货运车辆/运行特征")
  @Requirement(value = "获取货运车辆热点区域的出发量和到达量信息。并在地图中描绘出流向。")
  @Input(value = "时间颗粒、时段范围、查询类型、日期特征、开始日期、结束日期、车辆类型")
  @Output(value = "时间、日期、出发量、到达量")
  @Handle(value = "")
  @Parameter(value = "timeprecision，timeproperty，check_type，dateproperty，startdate，enddate，vehicle_type")
  public HttpResult<Object> getFreightTransportHpArrivalLeave(
      @RequestParam Map<String, Object> map) {

    return HttpResult.ok(specialtyAnalysisService.getFreightTransportHpArrivalLeave(map));
  }


  /**
   * 获取货运车辆货运通道信息.
   *
   * @param map startdate,enddate,timeproperty,vehicle_type,dateproperty
   */
  @ResponseBody
  @PostMapping(value = "getFreightTransportPass")
  @ApiOperation(value = "货运评估-货运车辆货运通道信息", notes = "货运车辆/运行特征")
  @Requirement(value = "获取获取车辆货运通道信息。并在地图中描绘出。")
  @Input(value = "开始日期、结束日期、车辆类型、时间颗粒、日期类型、小时")
  @Output(value = "道路名（fname）、起点（roadsect_from）、终点（roadsect_to）、时间/日期（time）、中路段id")
  @Handle(value = " ")
  @Parameter(value = "startdate,enddate,vehicle_type,timeprecision,date,hh")
  public HttpResult<Object> getFreightTransportPass(@RequestParam Map<String, Object> map) {

    return HttpResult.ok(specialtyAnalysisService.getFreightTransportPass(map));
  }


  /**
   * 获取货运车辆货运通道实时数据信息.
   *
   * @param map 为空
   */
  @ResponseBody
  @PostMapping(value = "getFreightTransportPassReal")
  @ApiOperation(value = "货运评估-货运车辆货运通道实时数据信息", notes = "货运车辆/运行特征")
  @Requirement(value = "获取货运车辆货运通道实时数据信息，并按照流量从大到小排序。")
  @Input(value = "无。")
  @Output(value = "id、流量、速度、方向、百分比、道路名称")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> getFreightTransportPassReal(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(specialtyAnalysisService.getFreightTransportPass(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadSixDData")
  @ApiOperation(value = "道路画像（六期）-六维特征")
  @Requirement(value = "获取全市高速路、快速路、主干道、次干道的六维特征信息。")
  @Input(value = "日期、高峰")
  @Output(value = "值串、路类型名、类型id")
  @Handle(value = "")
  @Parameter(value = "date,timeproperty")
  public HttpResult<Object> getRoadSixDData(RoadPortraitEntity dto) {
    return HttpResult.ok(specialtyAnalysisService.getRoadSixDData(dto));
  }

  @ResponseBody
  @PostMapping(value = "getRoadLevelDistribution")
  @ApiOperation(value = "道路画像(六期)-各等级道路路况分布")
  @Requirement(value = "获取个等级道路分布情况。并按照既定格式在前端页面展现。")
  @Input(value = "日期、高峰、日期特征")
  @Output(value = "是否拥堵名、值数组")
  @Handle(value = "后台会把查询的值，按照拥堵等级排列，组装成相应的数组。")
  @Parameter(value = "")
  public HttpResult<Object> getRoadLevelDistribution(RoadLevelDistributionEntity dto) {
    return HttpResult.ok(specialtyAnalysisService.getRoadLevelDistribution(dto));
  }

  @ResponseBody
  @PostMapping(value = "getRoadCharacteristicData")
  @ApiOperation(value = "道路画像（六期）-中路段特征信息")
  @Requirement(value = "获取中路段特征信息。")
  @Input(value = "日期、高峰、中路段id")
  @Output(value = "道路id、道路名称、道路起点、道路终点、道路设置、速度、速度比、指数、指数环比")
  @Handle(value = "后台会同时获取查询日期和查询日期的上一周对应的日期的值，计算出速度比和指数环比，然后组装成对象返回。")
  @Parameter(value = "date、timeproperty、id")
  public HttpResult<RoadCharacteristicDto> getRoadCharacteristicData(RoadPortraitEntity entity) {
    return HttpResult.ok(specialtyAnalysisService.getRoadCharacteristicData(entity));
  }

  @ResponseBody
  @PostMapping(value = "getRoadPortrayalTag")
  @ApiOperation(value = "道路画像（六期）-道路画像标签")
  @Requirement(value = "获取道路画像标签信息。")
  @Input(value = "日期、中路段id")
  @Output(value = "高峰运行状态、是否常发路段、拥堵程度")
  @Handle(value = "")
  @Parameter(value = "date，id")
  public HttpResult<RoadPortrayalTagDto> getRoadPortrayalTag(RoadPortrayalTagEntity entity) {
    return HttpResult.ok(specialtyAnalysisService.getRoadPortrayalTag(entity));
  }

  @ResponseBody
  @PostMapping(value = "getRoadsectDistribution")
  @ApiOperation(value = "道路画像-中路段地图显示是否常发拥堵路段数据集")
  @Requirement(value = "获取中路段是否常发拥堵路段数据集，并在地图中标记。")
  @Input(value = "日期")
  @Output(value = "中路段id、是否常发拥堵路段")
  @Handle(value = "")
  @Parameter(value = "date")
  public HttpResult<List<RoadsectDistributionDto>> getRoadsectDistribution(@RequestParam int date) {
    return HttpResult.ok(specialtyAnalysisService.getRoadsectDistribution(date));
  }

  @ResponseBody
  @PostMapping(value = "getRoadSixDDataById")
  @ApiOperation(value = "道路画像-指定路段六维画像数据")
  @Requirement(value = "获取指定道路的六维画像数据（拥堵里程比例、高峰速度迁徙程度、高峰速度偏离系数、高峰速度稳定性、高峰交通运行指数、拥堵持续时长。）")
  @Input(value = "中路段id、日期、高峰")
  @Output(value = "拥堵里程比例、高峰速度迁徙程度、高峰速度偏离系数、高峰速度稳定性、高峰交通运行指数、拥堵持续时长")
  @Handle(value = "后台会根据既定公式单独计算每个值，然后组装成既定格式返回。")
  @Parameter(value = "")
  public HttpResult<List<Map<String, Object>>> getRoadSixDDataById(
      RoadPortrayalRoadsectEntity entity) {
    return HttpResult.ok(specialtyAnalysisService.getRoadSixDDataById(entity));
  }

  @ResponseBody
  @GetMapping(value = "getRoadSectionJamCounts")
  @ApiOperation(value = "道路画像-拥堵路段数")
  @ApiImplicitParam(value = "道路画像日期", name = "date", defaultValue = "202006", required = true)
  @Requirement(value = "获取拥堵路段条数。")
  @Input(value = "日期")
  @Output(value = "拥堵条数")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<RoadSectionJamCountDto> getRoadSectionJamCounts(@RequestParam int date) {
    return HttpResult.ok(specialtyAnalysisService.getRoadSectionJamCounts(date));
  }
}
