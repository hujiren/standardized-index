package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.VideoService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ResponseBody
    @PostMapping(value = "getVideoLocation")
    @ApiModelProperty(value = "获取视频点位信息",notes = "运行一张图")
    public HttpResult<Object> getVideoLocation(@RequestParam Map<String,Object> map){
        return HttpResult.ok(videoService.getVideoLocation(map));
    }
}
