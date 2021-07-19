package com.sutpc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sutpc.service.Sh1Service;

@Controller
@RequestMapping(value = "/access")
public class Sh1Controller {

	@Autowired
	private Sh1Service sh1Service;

	@ResponseBody
	@RequestMapping(value = "/getMapListData", method = RequestMethod.GET)
	public Map<String, Object> getMapListData(HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (map.containsKey("mode") && map.containsKey("peak") && map.containsKey("pa")
				&& map.containsKey("hp")) {
			if ((map.get("mode").toString().equals("driving") || map.get("mode").toString().equals("transit"))
					&& (map.get("pa").toString().equals("p") || map.get("pa").toString().equals("a"))
					//&& Integer.parseInt(map.get("hp").toString()) >= 1
					//&& Integer.parseInt(map.get("hp").toString()) <= 3
					) {
				resultMap.put("info", "success");
				resultMap.put("status", "ok");
				if (map.get("pa").toString().equals("a")) {
					resultMap.put("list", sh1Service.selectGrid2HotpointData(map));
				} else {
					resultMap.put("list", sh1Service.selectHotpoint2GridData(map));
				}
			} else {
				resultMap.put("info", "failure");
				resultMap.put("status", "nok");
			}
		} else {
			resultMap.put("info", "failure");
			resultMap.put("status", "nok");
		}
		return resultMap;
	}
}
