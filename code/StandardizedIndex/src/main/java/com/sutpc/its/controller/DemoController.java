package com.sutpc.its.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutpc.its.model.DemoModel;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.model.PageModel;
import com.sutpc.its.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/demo")
@Api(tags = "Demo接口测试")
public class DemoController {

  @Autowired
  private DemoService dmService;

  //查询-Get，<=3个参数，>3用实体类封装
  //更新-Post
  //Lombak
  //liquibase后期上

  //powerdesiner
  //网络拓扑图，部署示意图
  //数据流向图

  //注释风格
  //阿里巴巴手册

  //需求输入
  //产品原型
  //
  //技术经理需求分析，功能分解，出架构，网络拓扑图等
  //
  //针对功能分解出业务流程图
  //
  //技术人员根据原型和上述步骤进行数据建模
  //
  //创建仓库，运行环境进行开发
  //测试
  //bug修复


  /**
   * .
   */
  @PostMapping(value = "/findObjects")
  @ApiOperation(value = "Demo列表返回", notes = "列表返回")
  public HttpResult<List<DemoModel>> findObjects(@RequestBody DemoModel map) {

    //统一结果返回
    return HttpResult.ok(dmService.findObjects(map));
  }

  /**
   * .
   */
  @PostMapping(value = "/findObjectsByPage")
  @ApiOperation(value = "Demo多条件查询列表分页", notes = "多条件查询列表分页")
  public HttpResult<PageInfo<DemoModel>> findObjectsByPage(PageModel pm,
      DemoModel queryMap) {

    PageHelper.startPage(pm.getPageNum(), pm.getPageSize());

    PageInfo<DemoModel> appsPageInfo = new PageInfo<DemoModel>(
        dmService.findObjects(queryMap)
    );

    return HttpResult.ok(appsPageInfo);

  }

  /*@ApiOperation(value = "Demo列表返回2", notes = "列表返回2")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String"),
      @ApiImplicitParam(name = "name", value = "名字", dataType = "String")
  })
  @PostMapping(value = "/findObjects2")
  public HttpResult findObjects2(@RequestParam(defaultValue = "1") String id, String name) {

    return HttpResult.ok(id + "," + name);
  }*/

  /**
   * .
   */
  @PostMapping(value = "/saveObject")
  @ApiOperation(value = "Demo保存数据", notes = "保存数据")
  public HttpResult<Integer> saveObject(DemoModel dm) {
    return HttpResult.ok(dmService.saveObject(dm));

  }

  /**
   * .
   */
  @PostMapping(value = "/updateObject")
  @ApiOperation(value = "Demo更新数据", notes = "更新数据")
  public HttpResult<Integer> updateObject(DemoModel dm) {
    return HttpResult.ok(dmService.updateObject(dm));

  }


  @Autowired
  private RestTemplate restTemplate;

  /**
   * .
   */
  @PostMapping(value = "/testRest")
  @ApiOperation(value = "DemoHttp请求发送")
  public HttpResult testRest() throws UnsupportedEncodingException {

    String encodeKey = URLEncoder.encode("我", "UTF-8");

    String url =
        "https://market.aliyun.com/products/57096001/cmapi012362.html?spm=5176.2020520132.101.4.aa8qWG#sku=yuncode636200004?wd="
            + encodeKey;

    return HttpResult.ok(restTemplate.getForObject(url, String.class));
  }

  /**
   * .
   */
  @PostMapping(value = "/testException")
  @ApiOperation(value = "Demo统一异常", notes = "统一异常")
  public void testException() throws Exception {

    throw new Exception("测试统一异常返回");
  }

  /**
   * .
   */
  @GetMapping(value = "/testAsync")
  @ApiOperation(value = "Demo异步方法")
  public HttpResult testAsync() {

    for (int i = 0; i < 10; i++) {
      dmService.execAsyncFunc();
    }

    return HttpResult.ok();
  }

  /**
   * .
   */
  @ResponseBody
  @RequestMapping("/getInfo")
  public List<Map<String, Object>> getBaseDistrictInfo(@RequestParam Map<String, Object> map) {
    return dmService.getBaseDistrictInfo(map);
  }

}
