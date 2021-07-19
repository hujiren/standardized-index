package com.sutpc.its.controller;

import com.sutpc.its.mail.MailUtils;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.StatementService;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.statement.bean.StatementValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/statement")
@Api(tags = "报告")
public class StatementController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private StatementService statementService;

  /**
   * .
   */
  @PostMapping("/build")
  @ApiOperation(value = "获取组合报告结果", notes = "获取组合报告结果")
  public HttpResult<StatementValue> build(StatementParam param) {
    logger.info("params = {}", param);
    StatementValue value = statementService.build(param);
    logger.info("result = {}", value);
    return HttpResult.ok(value);
  }

  /**
   * 查询所有模块模板.
   *
   * @param categoryId 模块类型
   * @return 模板列表
   */
  @PostMapping("/template")
  @ApiOperation(value = "获取所有模板", notes = "获取所有模板")
  public HttpResult<List<ModuleTemplate>> template(
      @RequestParam(required = false) Integer categoryId) {
    logger.info("params = {}", categoryId);
    List<ModuleTemplate> templates = statementService.template(categoryId);
    logger.info("result = {}", templates);
    return HttpResult.ok(templates);
  }

  /**
   * 导出报告.
   */
  @ResponseBody
  @PostMapping("/export")
  @ApiOperation(value = "导出报告", notes = "导出报告")
  public HttpResult<String> export(@RequestBody List<ModuleValue> values) {
    logger.info("params = {}", values);
    String filePath = statementService.export(values);
    logger.info("result = {}", filePath);
    return HttpResult.ok(filePath);
  }

  /**
   * 下载已经生成好的文件.
   */
  @ApiImplicitParam(value = "文件路径", name = "filePath", required = true)
  @ApiOperation(value = "下载文件")
  @GetMapping("/downLoad")
  public void download1File(HttpServletResponse response,
      String filePath) {
    statementService.download1File(response, filePath);
  }

  /**
   * 获取监测报告-月报数据.
   */
  @PostMapping(value = "getMonthReport")
  @ApiOperation(value = "监测报告-月报数据")
  public HttpResult<Object> getMonthReport(@RequestParam("date") String date,
      @RequestParam("districtFid") int districtFid) {
    return HttpResult.ok(statementService.getMonthReportData(date, districtFid));
  }

}
