package com.sutpc.its.aop;

import com.sutpc.its.model.HttpResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

  private Logger logger = LogManager.getLogger(ControllerException.class);

  /**
   * .
   */
  @ResponseBody
  @ExceptionHandler(value = Exception.class)
  public HttpResult errorHandler(Exception ex) {
    logger.error(ex.getMessage() + getStackMsg(ex));
    ex.printStackTrace();
    //return HttpResult.error(ex.getMessage());
    return HttpResult.error("error！");
  }

  private String getStackMsg(Exception e) {
    StringBuilder sb = new StringBuilder();
    StackTraceElement[] stackArray = e.getStackTrace();
    for (StackTraceElement element : stackArray) {
      sb.append(element.toString() + "\n");
    }
    return sb.toString();
  }
}
