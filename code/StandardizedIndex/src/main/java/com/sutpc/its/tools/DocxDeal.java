package com.sutpc.its.tools;

import com.github.pagehelper.util.StringUtil;
import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.vo.DocxResultVo;
import com.sutpc.its.vo.DocxSonVo;
import com.sutpc.its.vo.DocxVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:57 2020/11/23.
 * @Description
 * @Modified By:
 */
public class DocxDeal {

  public static void main(String[] args) {
    List<String> bean = PackageUtils.getClassNames("com.sutpc.its.controller");
    DocxResultVo docxResultVo = new DocxResultVo();
    List<DocxVo> result = new ArrayList<>();
    docxResultVo.setResult(result);
    for (String name : bean) {
      try {
        Class<?> cls = Class.forName(name);
        //有扫描注解的才进入
        if (cls.isAnnotationPresent(Scanning.class)) {
          DocxVo docxVo = new DocxVo();
          result.add(docxVo);
          //模块说明
          if (cls.isAnnotationPresent(Api.class)) {
            Api api = cls.getAnnotation(Api.class);
            String[] str = api.tags();
            docxVo.setModuleName(api.tags()[0]);
          }

          ///接口前缀获取
          String prefix = "";
          if (cls.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = cls.getAnnotation(RequestMapping.class);
            requestMapping.value();
            prefix = requestMapping.value()[0];
            if (!StringUtil.isEmpty(prefix) && !prefix.startsWith("/")) {
              prefix = "/" + prefix;
            }
          }
          //** 内层方法循环遍历 */
          List<DocxSonVo> list = new ArrayList<>();
          docxVo.setListSon(list);
          Method[] methods = cls.getDeclaredMethods();
          for (Method method : methods) {
            DocxSonVo docxSonVo = new DocxSonVo();
            list.add(docxSonVo);
            //1.读取swagger注释，获取方法描述
            if (method.isAnnotationPresent(ApiOperation.class)) {
              ApiOperation apiModelProperty = method.getAnnotation(ApiOperation.class);
              if (apiModelProperty.value().endsWith("。")) {
                docxSonVo.setName(apiModelProperty.value());
              } else {
                docxSonVo.setName(apiModelProperty.value() + "。");
              }

            }
            //2.获取需求描述
            if (method.isAnnotationPresent(Requirement.class)) {
              Requirement requirement = method.getAnnotation(Requirement.class);
              if (requirement.value().endsWith("。")) {
                docxSonVo.setRequirement(requirement.value());
              } else {
                docxSonVo.setRequirement(requirement.value() + "。");
              }

            }
            //3.获取输入描述
            if (method.isAnnotationPresent(Input.class)) {
              Input input = method.getAnnotation(Input.class);
              if (input.value().endsWith("。")) {
                docxSonVo.setInput(input.value());
              } else {
                docxSonVo.setInput(input.value() + "。");
              }

            }
            //4.获取输出描述
            if (method.isAnnotationPresent(Output.class)) {
              Output output = method.getAnnotation(Output.class);
              if (output.value().endsWith("。")) {
                docxSonVo.setOutput(output.value());
              } else {
                docxSonVo.setOutput(output.value() + "。");
              }
            }
            //5.获取处理信息
            if (method.isAnnotationPresent(Handle.class)) {
              Handle handle = method.getAnnotation(Handle.class);
              if (handle.value().endsWith("。")) {
                docxSonVo.setHandle(handle.value());
              } else {
                docxSonVo.setHandle(handle.value() + "。");
              }
            }
            //6.获取接口名称以及请求方式：暂时只写增删改查四个。
            if (method.isAnnotationPresent(PostMapping.class)) {
              docxSonVo.setMethod("POST");
              PostMapping postMapping = method.getAnnotation(PostMapping.class);
              String url = postMapping.value()[0];
              if (!StringUtil.isEmpty(url) && url.startsWith("/")) {
                docxSonVo.setUrl(prefix + url);
              } else {
                docxSonVo.setUrl(prefix + "/" + url);
              }
            } else if (method.isAnnotationPresent(GetMapping.class)) {
              docxSonVo.setMethod("GET");
              GetMapping getMapping = method.getAnnotation(GetMapping.class);
              String url = getMapping.value()[0];
              if (!StringUtil.isEmpty(url) && url.startsWith("/")) {
                docxSonVo.setUrl(prefix + url);
              } else {
                docxSonVo.setUrl(prefix + "/" + url);
              }
            } else if (method.isAnnotationPresent(PutMapping.class)) {
              docxSonVo.setMethod("PUT");
              PutMapping putMapping = method.getAnnotation(PutMapping.class);
              String url = putMapping.value()[0];
              if (!StringUtil.isEmpty(url) && url.startsWith("/")) {
                docxSonVo.setUrl(prefix + url);
              } else {
                docxSonVo.setUrl(prefix + "/" + url);
              }
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
              docxSonVo.setMethod("DELETE");
              DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
              String url = deleteMapping.value()[0];
              if (!StringUtil.isEmpty(url) && url.startsWith("/")) {
                docxSonVo.setUrl(prefix + url);
              } else {
                docxSonVo.setUrl(prefix + "/" + url);
              }
            }
          }
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    try {
      DocxUtil.getDocxFromTemplate("D://generalreport//test//design.docx",
          "D://generalreport//new//abcd.docx", docxResultVo);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}

