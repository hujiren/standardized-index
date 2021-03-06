package com.sutpc.its.aop;

import com.sutpc.its.config.Config;
import com.sutpc.its.tools.JwtUtils;
import com.sutpc.its.tools.PeriodUtils;
import io.jsonwebtoken.Claims;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Repository("SzTiBizServiceAop")
public class ServiceAop {

  /*@Value("${tpi.user_defined_base_district_fid}")
  private String user_defined_base_district_fid;*/

  @Value("${tpi.is_show}")
  private String isShow;

  @Value("${tpi.operation_ranking_road_type_fid}")
  private String operationRankingRoadTypeFid;

  @Value("${tpi.vehicle_type_fid}")
  private String vehicleTypeFid;

  @Value("${tpi.subsect_real_time_jam_tpi_critical_point}")
  private String subsectRealTimeJamTpiCriticalPoint;

  @Value("${tpi.subsect_real_time_jam_max}")
  private String subsectRealTimeJamMax;

  @Value("${tpi.poi_real_time_jam_tpi_critical_point}")
  private String poiRealTimeJamTpiCriticalPoint;

  @Value("${tpi.poi_real_time_jam_max}")
  private String poiRealTimeJamMax;

  @Value("${tpi.block_real_time_jam_tpi_critical_point}")
  private String blockRealTimeJamTpiCriticalPoint;

  @Value("${tpi.block_real_time_jam_max}")
  private String blockRealTimeJamMax;
  @Value("${tpi.high_speed_sample_vehicle}")
  private String highSpeedSampleVehicle;

  @Value("${tpi.roadsect_sample_vehile}")
  private String roadsectSampleVehile;

  @Value("${tpi.calender_road_type_fid}")
  private String calenderRoadTypeFid;

  @Value("${tpi.base_road_type_fid}")
  private String baseRoadTypeFid;

  @Value("${tpi.roadsect_flow_time}")
  private String roadsectFlowTime;

  @Value("${tpi.od_flag}")
  private String odFlag;

  @Value("${tpi.morning_start_period}")
  private String morningStartPeriod;
  @Value("${tpi.morning_end_period}")
  private String morningEndPeriod;
  @Value("${tpi.evening_start_period}")
  private String eveningStartPeriod;
  @Value("${tpi.evening_end_period}")
  private String eveningEndPeriod;
  @Value("${tpi.flat_start_period}")
  private String flatStartPeriod;
  @Value("${tpi.flat_end_period}")
  private String flatEndPeriod;
  /*
   * @AfterReturning(returning="rvt", value =
   * "execution(* com.sutpc.its.sztrafficindex.business.service.EarlyWarningService.*(..))"
   * ) public void beforeTiService(JoinPoint point,Map<String,Object> rvt)
   * throws Exception {
   *
   *
   * rvt.put("sects", ShiroUtils.getUserEntity()); System.out.println("");
   *
   * }
   */

  /**
   * .
   */
  @Before(value = "execution(* com.sutpc.its..service.*.*(..))")
  public void beforeTiBizSysService(JoinPoint point) throws Exception {
    Object[] args = point.getArgs();
    if (args.length == 1) {
      if (args[0] instanceof Map) {
        Map<String, Object> map = (Map<String, Object>) args[0];
        //start ???????????????????????????????????????
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
          String key = it.next();
          Object object = map.get(key);
          if (object instanceof String) {
            String value = String.valueOf(object);
            String filterString = stringFilter(value);
            if (value.length() != filterString.length()) {
              throw new Exception();
            }
          }
        }

        if (map.containsKey("config_district_fid")) {
          map.put("config_district_fid", map.get("config_district_fid").toString().split(","));
          Config.config_district_fid = map.get("config_district_fid").toString().split(",");
          map.put("config_district_fid_flag", 1);
        } else {
          HttpServletRequest hr = ((ServletRequestAttributes) RequestContextHolder
              .getRequestAttributes()).getRequest();
          String authorization = hr.getHeader("Authorization");
          if (authorization != null && !"".equals(authorization)) {
            String token = authorization.replace("bearer ", "");
            Claims claims = JwtUtils.getTokenBody(token);
            if (claims == null) {
              System.out.println("token??????:" + token + ",claims??????:" + claims);
            }
            String districts = "";
            if (claims != null) {
              districts = claims.get("r_expand") + "";
            }
            if (!"null".equals(districts) && !"".equals(districts)) {
              map.put("config_district_fid", districts.split(","));
              Config.config_district_fid = districts.split(",");
            } else {
              String permSects = hr.getHeader("permSects");
              if (permSects == null || "".equals(permSects)) {
                map.put("config_district_fid", "".split(","));
              } else {
                map.put("config_district_fid", permSects.split(","));
                Config.config_district_fid = permSects.split(",");
              }
            }
          } else {
            String permSects = hr.getHeader("permSects");
            if (permSects == null || "".equals(permSects)) {
              map.put("config_district_fid", "".split(","));
            } else {
              map.put("config_district_fid", permSects.split(","));
            }
          }
        }
        /* ??????????????????????????? */
        HttpServletRequest hr = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        String dataFlag = hr.getHeader("dataFlag");
        if (dataFlag != null && !"".equals(dataFlag)) {
          map.put("dataFlag", dataFlag);
        }

        /** ??????????????????/commonQuery/getDistrict **/
        /*if (!map.containsKey("user_defined_base_district_fid")) {
          //?????????????????????
          map.put("user_defined_base_district_fid", user_defined_base_district_fid.split(","));
        } else {
          //?????????????????????id
          map.put("user_defined_base_district_fid",
              map.get("user_defined_base_district_fid").toString().split(","));
        }*/

        if (!map.containsKey("is_show")) {
          map.put("is_show", isShow);
        }

        if (!map.containsKey("operation_ranking_road_type_fid")) {
          map.put("operation_ranking_road_type_fid", operationRankingRoadTypeFid.split(","));
        } else {
          map.put("operation_ranking_road_type_fid",
              map.get("operation_ranking_road_type_fid").toString().split(","));
        }

        if (!map.containsKey("vehicle_type_fid")) {
          map.put("vehicle_type_fid", vehicleTypeFid.split(","));
        } else {
          map.put("vehicle_type_fid", map.get("vehicle_type_fid").toString().split(","));
        }

        if (!map.containsKey("subsect_real_time_jam_tpi_critical_point")) {
          map.put("subsect_real_time_jam_tpi_critical_point", subsectRealTimeJamTpiCriticalPoint);
        }

        if (!map.containsKey("subsect_real_time_jam_max")) {
          map.put("subsect_real_time_jam_max", subsectRealTimeJamMax);
        }

        if (!map.containsKey("poi_real_time_jam_tpi_critical_point")) {
          map.put("poi_real_time_jam_tpi_critical_point", poiRealTimeJamTpiCriticalPoint);
        }

        if (!map.containsKey("poi_real_time_jam_max")) {
          map.put("poi_real_time_jam_max", poiRealTimeJamMax);
        }

        if (!map.containsKey("block_real_time_jam_tpi_critical_point")) {
          map.put("block_real_time_jam_tpi_critical_point", blockRealTimeJamTpiCriticalPoint);
        }

        if (!map.containsKey("block_real_time_jam_max")) {
          map.put("block_real_time_jam_max", blockRealTimeJamMax);
        }

        if (map.containsKey("hight_speed_real_time_jam_warning_districts")) {
          map.put("hight_speed_real_time_jam_warning_districts",
              map.get("hight_speed_real_time_jam_warning_districts").toString().split(","));
        }

        if (!map.containsKey("high_speed_sample_vehicle")) {
          map.put("high_speed_sample_vehicle", Integer.parseInt(highSpeedSampleVehicle));
        }

        if (!map.containsKey("roadsect_sample_vehile")) {
          map.put("roadsect_sample_vehile", Integer.parseInt(roadsectSampleVehile));
        }
        if (!map.containsKey("calender_road_type_fid")) {
          map.put("calender_road_type_fid", calenderRoadTypeFid.split(","));
        } else {
          map.put("calender_road_type_fid",
              map.get("calender_road_type_fid").toString().split(","));
        }

        if (!map.containsKey("base_road_type_fid")) {
          map.put("base_road_type_fid", baseRoadTypeFid.split(","));
        } else {
          map.put("base_road_type_fid", map.get("base_road_type_fid").toString().split(","));
        }

        if (!map.containsKey("roadsect_flow_time")) {
          map.put("roadsect_flow_time", roadsectFlowTime);
        }
        if (!map.containsKey("od_flag")) {
          map.put("od_flag", odFlag);
        }

        map.put("morning_start_period", Integer.parseInt(morningStartPeriod));
        map.put("morning_end_period", Integer.parseInt(morningEndPeriod));
        map.put("evening_start_period", Integer.parseInt(eveningStartPeriod));
        map.put("evening_end_period", Integer.parseInt(eveningEndPeriod));
        map.put("flat_start_period", Integer.parseInt(flatStartPeriod));
        map.put("flat_end_period", Integer.parseInt(flatEndPeriod));

        // ???????????????????????????????????????systemCurrentTime
        if (!map.containsKey("time")) {
          map.put("time", PeriodUtils.getCurrentDate());
          //???????????????????????????201710????????????????????????????????????
          if ("tongji".equals(odFlag)) {
            int weekdaynow = PeriodUtils.getWeekdayNumOfDate(new Date());
            //int weeknumnow=Integer.parseInt(PeriodUtils.getCurrentDate().substring(6,8))/7;
            //int time=20171001+weekdaynow+(weeknumnow>3?3:weeknumnow)*7;
            int time = 20171008 + weekdaynow;
            if (weekdaynow == 0) {
              time = 20171022;
            }
            map.put("time", time);
          }
        }

        if (!map.containsKey("period")) {
          map.put("period", PeriodUtils.getCurrentPeriod());
        }
        if (!map.containsKey("period15")) {
          //period15?????????????????????????????????????????????????????????
          //map.put("period15", (PeriodUtils.getCurrentPeriod() - 1) / 3 + 1);
          map.put("period15", (PeriodUtils.getCurrentPeriod()) / 3);

          //??????????????????????????????????????????????????????????????????????????????
          if ("tongji".equals(odFlag)) {
            String weekdaynow = PeriodUtils.getWeekdayOfDate(new Date());
            Date datetj = new SimpleDateFormat("yyyyMMdd").parse(map.get("time").toString());
            String weekdaytj = PeriodUtils.getWeekdayOfDate(datetj);
            if (!weekdaynow.equals(weekdaytj)) {
              map.put("period15", 96);
            }
          } else {
            //?????????????????????????????????????????????????????????????????????????????????????????????
            Date dateZjValue = new SimpleDateFormat("yyyyMMdd").parse(map.get("time").toString());
            String datezj = new SimpleDateFormat("yyyyMMdd").format(dateZjValue);
            String datenow = PeriodUtils.getCurrentDate();
            if (!datezj.equals(datenow)) {
              map.put("homePagePeriod15", 96);
            }
          }
        }
        if (!map.containsKey("period30")) {
          map.put("period30", (PeriodUtils.getCurrentPeriod() + 1) / 6 - 1);
        }
        if (!map.containsKey("period_hour")) {
          map.put("period_hour", (PeriodUtils.getCurrentPeriod() - 1) / 12);
        }
      }

    }

  }

  private String stringFilter(String str) {
    // ???????????????????????? // String regEx ="[^a-zA-Z0-9]";
    // ???????????????????????????
    //String regEx = "[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~???@#???%??????&*??????+|{}????????????????????????????????????]";
    String regEx = "[/<>????????????????????????????????????????@#???%??????&*]";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    return m.replaceAll("").trim();
  }

}
