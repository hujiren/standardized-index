package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.ZjCustomizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/zjcustom")
@Api
public class ZjcustomzationController {

  @Autowired
  private ZjCustomizationService zjCustomizationService;

  @ResponseBody
  @PostMapping("getHisTpi")
  @ApiModelProperty(value = "历史回溯-湛江")
  public HttpResult<Object> getHisTpi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(zjCustomizationService.getHisTpi(map));
  }
}
