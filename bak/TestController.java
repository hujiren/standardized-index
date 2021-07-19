package com.sutpc.its.controller;

import com.sutpc.its.annotation.*;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.SpecialtyAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuJiRen
 * @date 2021/4/13 14:54
 */
@RestController
@RequestMapping(value = "/specialtyAnalysis")
@Api(tags = "测试")
@Scanning
public class TestController {

    @Autowired
    SpecialtyAnalysisService specialtyAnalysisService;

    /**
     * test  获取节假日查询结果
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getHolidayQueryResultTest")
    @ApiOperation(value = "假日评估-获取节假日查询结果", notes = "假日评估")
    @Requirement(value = "获取指定节假日的热点运行情况及热点周边路段运行情况。")
    @Input(value = "年份、节假日id、热点类型、时间范围、道路类型")
    @Output(value = "拥堵时长、路段名称、路段起点、路段终点、拥堵时间、方向、拥堵速度")
    @Handle(value = "后台会把拿到的id转换为数据库查询需要的数据格式，查询出结果，并转换成前端需要的数据格式。")
    @Parameter(value = "year，holiday_fid，spot_type，stage，road_type_fid")
    public HttpResult<Object> getHolidayQueryResultTest() {
//    @RequestParam Map<String, Object> map
        Map<String, Object> map  = new HashMap<>();
        map.put("year", "2019");
        map.put("holiday_fid", "2");
        map.put("spot_type", "2,4");
        map.put("stage", "1");
        map.put("road_type_fid", "1,2,3,4");
        Map<String, Object> holidayQueryResultTest = specialtyAnalysisService.getHolidayQueryResultTest(map);
        return HttpResult.ok(holidayQueryResultTest);
    }


    /**
     * test 热点周围中路段拥堵情况
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getSpotAroundJamRoadsectStatusTest")
    @ApiOperation(value = "假日评估-热点周围中路段拥堵情况", notes = "假日评估")
    @Requirement(value = "获取指定节假日热点周围中路段的拥堵情况。")
    @Input(value = "年份、节假日id、热点类型、时间范围、道路类型")
    @Output(value = "中路段id、指数、热点id、速度、热点类型、运行状态")
    @Handle(value = "后台会把拿到的id转换为数据库查询需要的数据格式，查询出结果，并转换成前端需要的数据格式。")
    @Parameter(value = "year，holiday_fid，spot_type，stage，road_type_fid")
    public HttpResult<Object> getSpotAroundJamRoadsectStatusTest() {
//    @RequestParam Map<String, Object> map
        Map<String, Object> map  = new HashMap<>();
        map.put("year", "2019");
        map.put("holiday_fid", "2");
        map.put("spot_type", "2,4");
        map.put("stage", "1");
        map.put("road_type_fid", "1,2,3,4");
        return HttpResult.ok(specialtyAnalysisService.getSpotAroundJamRoadsectStatusTest(map));
    }

    /**
     *.test
     */
    public List<Map<String, Object>> getSpotAroundJamRoadsectStatusTest(Map<String, Object> map) {

        String[] config_district_fid = (String[]) map.get("config_district_fid");

        StringBuilder sb = new StringBuilder();
        for (String s : config_district_fid) {
            if(sb.length() > 0)
                sb.append(",");
            sb.append(s);
        }
        map.put("config_district_fid", sb.toString());
        String spotType = map.get("spot_type").toString();
        if (spotType.contains("0")) {
            map.put("otherFlag", 0);
        }
        return specialtyAnalysisDao.getSpotAroundJamRoadsectStatusTest(map);
    }

    /**
     * 假日评估-获取节假日查询结果test
     */
    public Map<String, Object> getHolidayQueryResultTest(Map<String, Object> map) {

        String[] config_district_fid = (String[]) map.get("config_district_fid");

        StringBuilder sb = new StringBuilder();
        for (String s : config_district_fid) {
            if(sb.length() > 0)
                sb.append(",");
            sb.append(s);
        }
        map.put("config_district_fid", sb.toString());
        String roadTypeFid = map.get("road_type_fid").toString();

        String spotType = map.get("spot_type").toString();
        if (spotType.contains("0")) {
            map.put("otherFlag", 0);
        }
        List<Map<String, Object>> list = specialtyAnalysisDao.getHolidayQueryResultTest(map);

        String excelFileName =
                getHolidayName(map.get("holiday_fid") + "") + "_" + map.get("year") + "_" +
                        getRoadName(roadTypeFid) + getHolidayType(map.get("stage") + "") + "_表_";
        String downloadName = Utils.getDownloadFileName(map, excelFileName, commonQueryDao);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", list);

        List<String> keys = new ArrayList<String>() {
            {
                add("JAM_HOUR");
                add("ROADSECT_NAME");
                add("ROADSECT_TO");
                add("JAM_PERIOD");
                add("ROADSECT_FROM");
                add("ROADSECT_FID");
                add("JAM_WEIGHT");
                add("DIRNAME");
                add("JAM_SPEED");
                add("SPOTSNAME");
            }
        };

        result.put("filepath", ExcelUtils.exportToExcel(keys, list, downloadName, "", ""));
        return result;
    }
}
