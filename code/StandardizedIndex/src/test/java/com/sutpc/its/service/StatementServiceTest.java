package com.sutpc.its.service;

import static org.junit.Assert.assertNotEquals;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sutpc.its.dao.IReportDao;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.statement.bean.StatementValue;
import com.sutpc.its.tools.DateUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 报告测试类.
 *
 * @author admin
 * @date 2020/5/20 14:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class StatementServiceTest {

  @Autowired
  private StatementService statementService;
  @Autowired
  private IReportDao dao;

  @Value("${spring.datasource.url}")
  private String url;



  @Test
  public void getUrl(){
    System.out.println(url);
  }

  @Test
  public void buildForCity() {
    //计算几个月前
    int afterMonthNum = -6;
    for (int k = -6; k < 0; k++) {
      LocalDate localDate = DateUtils.monthEnd(k);
      String time = localDate.atStartOfDay().toString().replace("-", "").substring(0, 8);
      LocalDate date = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd"));
      LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
      LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
      int currentStartDate = Integer.parseInt(first.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
      int currentEndDate = Integer.parseInt(last.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

      //环比日期获取
      LocalDate cycleLocalDate = DateUtils.monthEnd(k-1);
      String cycleTime = cycleLocalDate.atStartOfDay().toString().replace("-", "").substring(0, 8);
      LocalDate cycleDate = LocalDate.parse(cycleTime, DateTimeFormatter.ofPattern("yyyyMMdd"));
      LocalDate cycleFirst = cycleDate.with(TemporalAdjusters.firstDayOfMonth());
      LocalDate cycleLast = cycleDate.with(TemporalAdjusters.lastDayOfMonth());
      int cycleStartDate = Integer.parseInt(cycleFirst.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
      int cycleEndDate = Integer.parseInt(cycleLast.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

      //同比日期获取
      LocalDate lastLocalDate = localDate.plusYears(-1);
      String lastTime = lastLocalDate.atStartOfDay().toString().replace("-", "").substring(0, 8);
      LocalDate lastDate = LocalDate.parse(lastTime, DateTimeFormatter.ofPattern("yyyyMMdd"));
      LocalDate lastFirst = lastDate.with(TemporalAdjusters.firstDayOfMonth());
      LocalDate lastLast = lastDate.with(TemporalAdjusters.lastDayOfMonth());
      int lastStartDate = Integer.parseInt(lastFirst.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
      int lastEndDate = Integer.parseInt(lastLast.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

      System.out.println("开始日期："+currentStartDate);
      System.out.println("结束日期："+currentEndDate);
      System.out.println("环比开始："+cycleStartDate);
      System.out.println("环比结束："+cycleEndDate);
      System.out.println("同比开始："+lastStartDate);
      System.out.println("同比结束："+lastEndDate);

      System.out.println("-----------------------------------------");
      for (int i = 1; i <= 10; i++) {
        calData(i,currentStartDate,currentEndDate,cycleStartDate,cycleEndDate,lastStartDate,lastEndDate);
      }
    }
    System.out.println("***************************************计算结束************************************");
  }

  @Test
  public void buildForDistrict() {
    StatementParam param = new StatementParam();
    //"MODULE_DISTRICT_TOTAL_NET","MODULE_DISTRICT_BLOCK","MODULE_DISTRICT_MAIN_ROAD"
    // ,"MODULE_DISTRICT_HOT_SPOT"
    //"MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
    //            "MODULE_DISTRICT_BUSINESS", "MODULE_DISTRICT_SCENIC", "MODULE_DISTRICT_PORT"
    //拼装月报的模块，不可乱顺序
    param.setTypes(Arrays
        .asList("MODULE_DISTRICT_HEAD", "MODULE_DISTRICT_OVERVIEW", "MODULE_DISTRICT_TOTAL_NET",
            "MODULE_DISTRICT_BLOCK", "MODULE_DISTRICT_MAIN_ROAD", "MODULE_DISTRICT_HOT_SPOT",
            "MODULE_DISTRICT_HOSPITAL", "MODULE_DISTRICT_SCHOOL", "MODULE_DISTRICT_HINGE",
            "MODULE_DISTRICT_BUSINESS", "MODULE_DISTRICT_SCENIC", "MODULE_DISTRICT_PORT",
            "MODULE_DISTRICT_THIS_ROAD_SECTION", "MODULE_DISTRICT_LAST_ROAD_SECTION",
            "MODULE_DISTRICT_WORSE", "MODULE_DISTRICT_IMPROVE", "MODULE_DISTRICT_FOOT"));
    param.setCurrentStartDate(20200510);
    param.setCurrentEndDate(20200517);
    param.setCycleStartDate(20200503);
    param.setCycleEndDate(20200510);
    param.setYearStartDate(20190517);
    param.setYearEndDate(20190524);
    param.setDistrictId(1);
    StatementValue value = statementService.build(param);
    System.out.println(JSON.toJSONString(value));
    assertNotEquals(value, null);
  }

  public void calData(int districtId,int currentStartDate,int currentEndDate,int cycleStartDate,int cycleEndDate,int lastStartDate,int lastEndDate) {
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
//    //当前月的上一月开始日期
//    param.setCurrentStartDate(Integer.parseInt(DateUtils.monthStartStr(-1)));
//    //当前月的上一月结束日期
//    param.setCurrentEndDate(Integer.parseInt(DateUtils.monthEndStr(-1)));
//    //当前月的上一月的前一月开始日期（环比日期）
//    param.setCycleStartDate(Integer.parseInt(DateUtils.monthStartStr(-2)));
//    //当前月的上一月的前一月结束日期（环比日期）
//    param.setCycleEndDate(Integer.parseInt(DateUtils.monthEndStr(-2)));
//    //当前月的上一月的前一年开始日期（同比日期）
//    param.setYearStartDate(Integer.parseInt(DateUtils.monthStartStr(-13)));
//    //当前月的上一月的前一年结束日期（同比日期）
//    param.setYearEndDate(Integer.parseInt(DateUtils.monthEndStr(-13)));

    //当前月的上一月开始日期
    param.setCurrentStartDate(currentStartDate);
    //当前月的上一月结束日期
    param.setCurrentEndDate(currentEndDate);
    //当前月的上一月的前一月开始日期（环比日期）
    param.setCycleStartDate(cycleStartDate);
    //当前月的上一月的前一月结束日期（环比日期）
    param.setCycleEndDate(cycleEndDate);
    //当前月的上一月的前一年开始日期（同比日期）
    param.setYearStartDate(lastStartDate);
    //当前月的上一月的前一年结束日期（同比日期）
    param.setYearEndDate(lastEndDate);
    param.setDistrictId(districtId);
    StatementValue value = statementService.build(param);
    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(value);
    String id = UUID.randomUUID().toString().replace("-", "");
    String fdate = String.valueOf(currentStartDate).substring(0, 6);
    String key = fdate + "_" + districtId;
    int num = dao.setMonthReport(id, fdate, districtId, jsonObject.toJSONString(), key);
    log.info("[定时任务-计算月报-id={}的行政区完成入库]", districtId);
  }

  @Test
  public void testMonthSet() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 20000; i++) {
      stringBuilder.append(i + ",");
    }
    String id = "0987654321";
    int districtFid = 1;
    int cout = dao.setMonthReport(id, "202008", districtFid, stringBuilder.toString(), "202010_1");
  }

  @Test
  public void testMonthGet() {
    Map<String, Object> map = dao.getMonthReport("202008_1");
    Clob clob = (Clob) map.get("JSON");
    try {
      System.out.println(clobToString(clob));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * .
   */
  public String clobToString(Clob clob) throws SQLException, IOException {
    String ret = "";
    Reader read = clob.getCharacterStream();
    BufferedReader br = new BufferedReader(read);
    String s = br.readLine();
    StringBuffer sb = new StringBuffer();
    while (s != null) {
      sb.append(s);
      s = br.readLine();
    }
    ret = sb.toString();
    if (br != null) {
      br.close();
    }
    if (read != null) {
      read.close();
    }
    return ret;
  }
}
