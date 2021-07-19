package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.SzReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:07 2020/3/2.
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping(value = "szReport")
@Api(tags = "深圳-报告")
public class SzReportController {

  @Autowired
  private SzReportService szReportService;


}
