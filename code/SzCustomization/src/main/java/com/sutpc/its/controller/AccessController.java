package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.AccessService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/access")
public class AccessController {
    @Autowired
    private AccessService accessService;

    @ResponseBody
    @PostMapping("/getMapListData")
    @ApiModelProperty(value = "可达性")
    public HttpResult<Object> getMapListData(HttpServletRequest request, @RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (map.containsKey("mode") && map.containsKey("peak") && map.containsKey("pa")
                && map.containsKey("hp")) {
            if ((map.get("mode").toString().equals("driving") || map.get("mode").toString().equals("transit"))
                    && (map.get("pa").toString().equals("p") || map.get("pa").toString().equals("a"))
            ) {
                resultMap.put("info", "success");
                resultMap.put("status", "ok");
                if (map.get("pa").toString().equals("a")) {
                    resultMap.put("list", accessService.selectGrid2HotpointData(map));
                } else {
                    resultMap.put("list", accessService.selectHotpoint2GridData(map));
                }
            } else {
                resultMap.put("info", "failure");
                resultMap.put("status", "nok");
            }
        } else {
            resultMap.put("info", "failure");
            resultMap.put("status", "nok");
        }
        return HttpResult.ok(resultMap);
    }

    @ResponseBody
    @PostMapping("/getTripTimeDistribution")
    @ApiModelProperty(value ="出行时间分布",notes = "可达范围")
    public HttpResult<Object> getTripTimeDistribution(@RequestParam Map<String,Object> map){
        return HttpResult.ok(accessService.getTripTimeDistribution(map));
    }
    @ResponseBody
    @PostMapping(value = "getTripDistanceDistribution")
    @ApiModelProperty(value = "出行距离分布", notes ="可达范围")
    public HttpResult<Object> getTripDistanceDistribution(@RequestParam Map<String, Object> map){
        return HttpResult.ok(accessService.getTripDistanceDistribution(map));
    }
}
