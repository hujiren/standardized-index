package com.sutpc.its.timertask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sutpc.its.dao.IReportDao;
import com.sutpc.its.dto.CapacityDto;
import com.sutpc.its.service.StatementService;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.statement.bean.StatementValue;
import com.sutpc.its.tools.DateUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:35 2020/8/14.
 * @Description
 * @Modified By:
 */
@Component
@Slf4j
public class ScheduledClass {

  public CapacityDto capacityDto = new CapacityDto();
  @Value("${tpi.netCount}")
  private String netUrl;
  @Value("${tpi.busCount}")
  private String busUrl;
  @Value("${tpi.taxiCount}")
  private String taxiUrl;

  @Value("${appUrl.isRunnable:true}")
  private boolean isRunnable;

  @Autowired
  private StatementService statementService;

  @Autowired
  private IReportDao dao;

  //@Scheduled(fixedRate = 120000)
  public void loadFtcCapacity() {
    getFtcCapacity();
  }

  /**
   * 缓存公交运力.
   */
  public CapacityDto getFtcCapacity() {
    CapacityDto dto = new CapacityDto();
    if (isRunnable) {
      dto.setBus(getCapacityNumber(busUrl));
      dto.setNet(getCapacityNumber(netUrl));
      dto.setTaxi(getCapacityNumber(taxiUrl));
      capacityDto = dto;
      log.info("[公交运力获取成功]>{}", dto);
    }
    return dto;
  }

  /**
   * 获取公交运力信息.
   */
  public int getCapacityNumber(String url) {
    try {
      HttpResponse<String> result = Unirest.get(url).asString();
      String body = result.getBody();
      Map<String, Object> resultMap = (Map<String, Object>) JSON.parse(body);
      Map<String, Integer> data = (Map<String, Integer>) resultMap.get("data");
      return data.get("allCount");
    } catch (UnirestException e) {
      return 0;
    }
  }

  /**
   * 每月1号的0:10:00执行.
   */
  @Scheduled(cron = "0 10 0 1 * ?")
  //@Scheduled(fixedRate = 500000000)
  public void loadCalculateDistrictMonthJson() {
    List<Integer> idList = dao.getDistrictIds();
    if (idList.size() > 0) {
      for (int districtId : idList) {
        calculateDistrictMonthJson(districtId);
      }
    }
  }

  /**
   * 计算月报Json数据.
   */
  public void calculateDistrictMonthJson(int districtId) {
    StatementParam param = new StatementParam();
    param.setTypes(Arrays
        .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
            "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
            "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
            "MODULE_DISTRICT_BUSINESS", "MODULE_DISTRICT_SCENIC", "MODULE_DISTRICT_PORT",
            "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
            "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
    //拼装月报的模块，不可乱顺序
    /**
     if(districtId == 1||districtId==2||districtId == 3){
     param.setTypes(Arrays
     .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
     "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
     "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
     "MODULE_DISTRICT_BUSINESS", "MODULE_DISTRICT_SCENIC", "MODULE_DISTRICT_PORT",
     "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
     "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
     }else if(districtId == 4){
     param.setTypes(Arrays
     .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
     "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
     "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
     "MODULE_DISTRICT_BUSINESS", "MODULE_DISTRICT_SCENIC",
     "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
     "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
     }else if(districtId == 5 || districtId == 6){
     param.setTypes(Arrays
     .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
     "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
     "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
     "MODULE_DISTRICT_BUSINESS",
     "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
     "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
     }else if (districtId == 7 || districtId == 8){
     param.setTypes(Arrays
     .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
     "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
     "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
     "MODULE_DISTRICT_SCENIC",
     "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
     "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
     }else if(districtId==9){
     param.setTypes(Arrays
     .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
     "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
     "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
     "MODULE_DISTRICT_BUSINESS",
     "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
     "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
     }else if (districtId == 10){
     param.setTypes(Arrays.asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
     "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD",
     "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
     "MODULE_DISTRICT_SCENIC",
     "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
     "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
     }
     **/
    // 删掉的部分。"MODULE_DISTRICT_HOT_SPOT",
    //当前月的上一月开始日期
    param.setCurrentStartDate(Integer.parseInt(DateUtils.monthStartStr(-1)));
    //当前月的上一月结束日期
    param.setCurrentEndDate(Integer.parseInt(DateUtils.monthEndStr(-1)));
    //当前月的上一月的前一月开始日期（环比日期）
    param.setCycleStartDate(Integer.parseInt(DateUtils.monthStartStr(-2)));
    //当前月的上一月的前一月结束日期（环比日期）
    param.setCycleEndDate(Integer.parseInt(DateUtils.monthEndStr(-2)));
    //当前月的上一月的前一年开始日期（同比日期）
    param.setYearStartDate(Integer.parseInt(DateUtils.monthStartStr(-13)));
    //当前月的上一月的前一年结束日期（同比日期）
    param.setYearEndDate(Integer.parseInt(DateUtils.monthEndStr(-13)));

//    //当前月的上一月开始日期
//    param.setCurrentStartDate(20201101);
//    //当前月的上一月结束日期
//    param.setCurrentEndDate(20201131);
//    //当前月的上一月的前一月开始日期（环比日期）
//    param.setCycleStartDate(20201001);
//    //当前月的上一月的前一月结束日期（环比日期）
//    param.setCycleEndDate(20201031);
//    //当前月的上一月的前一年开始日期（同比日期）
//    param.setYearStartDate(20191101);
//    //当前月的上一月的前一年结束日期（同比日期）
//    param.setYearEndDate(20191131);
    param.setDistrictId(districtId);
    StatementValue value = statementService.build(param);
    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(value);
    String id = UUID.randomUUID().toString().replace("-", "");
    String fdate = DateUtils.monthStartStr(-1).substring(0, 6);
    String key = fdate + "_" + districtId;
    int num = dao.setMonthReport(id, fdate, districtId, jsonObject.toJSONString(), key);
    log.info("[定时任务-计算月报-id={}的行政区完成入库]", districtId);
  }

}
